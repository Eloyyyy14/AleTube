package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eloyyyyyyy.pruebasapiyoutube.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

public class ActivityIniciarSesion extends AppCompatActivity {

    static final String SOAPACTION = "http://Server/mostarNombre";
    private static final String METHOD = "mostarNombre";
    private static final String NAMESPACE = "http://Server/";
    private static final String URL = "http://192.168.1.37:9137/Server/Server?wsdl";

    ImageView ivLogo;
    EditText etPassword, etUsuario;
    Button btnIniciarSesion, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        ivLogo=(ImageView)findViewById(R.id.ivLogo);
        etUsuario=(EditText) findViewById(R.id.etUsuario);
        etPassword=(EditText) findViewById(R.id.etPassword);
        btnIniciarSesion=(Button)findViewById(R.id.btnIniciarSesion);
        btnCancelar=(Button)findViewById(R.id.btnCancelar);
    }

    public void iniciarSesion(View v){
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
            PropertyInfo getUser = new PropertyInfo();
            getUser.setName("getUser");
            request.addProperty(getUser);

            //Llamada
            transporte.call(SOAPACTION, sobre);

            //Resultado
            Vector lista = new Vector();
            lista = (Vector) sobre.getResponse();

            String user = lista.get(0).toString();
            String pass = lista.get(1).toString();

            if(user.equals(etUsuario.getText().toString()) && pass.equals(etPassword.getText().toString())){
                Intent i = new Intent(ActivityIniciarSesion.this, ActivityMenuLateral.class);
                //Cerrar todas las activities anteriores y crear una nueva
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("usuario", lista.get(0).toString());
                startActivity(i);
            }

            else {
                Toast.makeText(getApplicationContext(),"Usuario/Contraseña incorrecto" ,Toast.LENGTH_LONG).show();

                etUsuario.setText("");
                etPassword.setText("");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelar(View v){
        Intent i = new Intent(ActivityIniciarSesion.this, ActivityLogin.class);
        startActivity(i);
    }
}
