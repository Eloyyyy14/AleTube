package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.VideoConVisitas;
import com.example.eloyyyyyyy.pruebasapiyoutube.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;
import java.util.Vector;

public class ActivityPerfil extends AppCompatActivity {

    static final String SOAPACTION = "http://Server/mostrarUsuarioCorreo";
    private static final String METHOD = "mostrarUsuarioCorreo";
    private static final String NAMESPACE = "http://Server/";
    private static final String URL = "http://192.168.1.37:9137/Server/Server?wsdl";

    TextView tvUser, tvCorreo;
    EditText etPassAc, etPassNueva;

    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvUser = (TextView)findViewById(R.id.tvUser);
        tvCorreo = (TextView)findViewById(R.id.tvCorreo);
        etPassAc = (EditText) findViewById(R.id.etPassAc);
        etPassNueva = (EditText)findViewById(R.id.etPassNueva);

        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");

        //Obtener el usuario y el correo

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
            StrictMode.setThreadPolicy(policy);

            //Creacion de la Solicitud
            SoapObject request = new SoapObject(NAMESPACE, METHOD);

            //Creacion del Envelope
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);

            //Creacion del transporte
            HttpTransportSE transporte = new HttpTransportSE(URL);

            // Paso de parámetro
            PropertyInfo getUserCorreo = new PropertyInfo();
            getUserCorreo.setName("getUserCorreo");
            request.addProperty(getUserCorreo);

            //Llamada
            transporte.call(SOAPACTION, sobre);

            //Resultado
            Vector lista = new Vector();
            lista = (Vector) sobre.getResponse();

            String user = lista.get(0).toString();
            String correo = lista.get(1).toString();
            pass = lista.get(2).toString();

            tvUser.setText(user);
            tvCorreo.setText(correo);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarCambios(View v){
        if(etPassNueva.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Introduzca un valor", Toast.LENGTH_LONG).show();
        }
        else{
            if(etPassAc.getText().toString().equals(pass)){
                Toast.makeText(getApplicationContext(), "Contraseña cambiada", Toast.LENGTH_LONG).show();
            }
            else{
                etPassAc.setText("");
                etPassNueva.setText("");

                Toast.makeText(getApplicationContext(), "Error esa no es la contraseña actual", Toast.LENGTH_LONG).show();
            }
        }
    }
}
