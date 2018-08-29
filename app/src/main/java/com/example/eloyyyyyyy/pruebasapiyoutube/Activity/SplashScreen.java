package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.eloyyyyyyy.pruebasapiyoutube.R;

public class SplashScreen extends AppCompatActivity {

    ProgressBar pbBarra;
    ImageView ivLogo;
    int progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pbBarra=(ProgressBar)findViewById(R.id.pbBarra);
        ivLogo=(ImageView)findViewById(R.id.ivLogo);

        ivLogo.setImageResource(R.drawable.imagenlogo);

        new Thread(miHilo).start();
    }

    private Runnable miHilo = new Runnable() {
        @Override
        public void run() {
            while(progreso < 100){
                try {

                    miHandle.sendMessage(miHandle.obtainMessage());
                    Thread.sleep(27);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (Throwable t){
                    t.printStackTrace();
                }
            }

            Intent i = new Intent(SplashScreen.this, ActivityMenuLateral.class);
            startActivity(i);
            finish();
        }

        Handler miHandle = new Handler(){

            public void handleMessage(Message msg) {
                progreso++;
                pbBarra.setProgress(progreso);
            }
        };
    };
}
