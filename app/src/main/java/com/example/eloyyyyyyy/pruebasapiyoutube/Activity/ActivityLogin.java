package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eloyyyyyyy.pruebasapiyoutube.R;

public class ActivityLogin extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvInicio;
    Button btnLogin, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivLogo=(ImageView)findViewById(R.id.ivLogo);
        tvInicio=(TextView)findViewById(R.id.tvInicio);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegistro=(Button)findViewById(R.id.btnRegistro);
    }

    public void login(View v){
        Intent i = new Intent(ActivityLogin.this, ActivityIniciarSesion.class);
        startActivity(i);
    }

    public void registro(View v){
        Intent i = new Intent(ActivityLogin.this, ActivityRegistro.class);
        startActivity(i);
    }
}
