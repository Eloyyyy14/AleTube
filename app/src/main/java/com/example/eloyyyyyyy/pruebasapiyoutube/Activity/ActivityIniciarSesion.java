package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.eloyyyyyyy.pruebasapiyoutube.R;

public class ActivityIniciarSesion extends AppCompatActivity {

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

    }

    public void cancelar(View v){
        Intent i = new Intent(ActivityIniciarSesion.this, ActivityLogin.class);
        startActivity(i);
    }
}
