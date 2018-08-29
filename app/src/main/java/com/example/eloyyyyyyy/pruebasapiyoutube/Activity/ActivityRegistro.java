package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eloyyyyyyy.pruebasapiyoutube.R;

public class ActivityRegistro extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvTitulo, tvUsuario, tvPassword, tvCorreoElectronico;
    EditText etPassword, etUsuario, etCorreoElectronico;
    Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ivLogo=(ImageView)findViewById(R.id.ivLogo);
        tvTitulo=(TextView)findViewById(R.id.tvTitulo);
        tvUsuario=(TextView)findViewById(R.id.tvUsuario);
        tvPassword=(TextView)findViewById(R.id.tvPassword);
        tvCorreoElectronico=(TextView)findViewById(R.id.tvCorreo);
        etUsuario=(EditText) findViewById(R.id.etUsuario);
        etPassword=(EditText) findViewById(R.id.etPassword);
        etCorreoElectronico=(EditText) findViewById(R.id.etCorreoElectronico);
        btnCrearCuenta=(Button)findViewById(R.id.btnCrearCuenta);
    }

    public void crearCuenta(View v){

    }
}
