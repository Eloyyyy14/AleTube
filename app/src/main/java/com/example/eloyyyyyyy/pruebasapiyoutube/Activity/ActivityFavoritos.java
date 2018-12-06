package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.StatsVideo;
import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.Video;
import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.VideoConVisitas;
import com.example.eloyyyyyyy.pruebasapiyoutube.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ActivityFavoritos extends AppCompatActivity {

    static final String SOAPACTION = "http://Server/mostrarFavoritos";
    private static final String METHOD = "mostrarFavoritos";
    private static final String NAMESPACE = "http://Server/";
    private static final String URL = "http://192.168.1.37:9137/Server/Server?wsdl";

    ListView lvFavoritos;
    VideoConVisitas videoConVisitas = new VideoConVisitas();
    ArrayList<VideoConVisitas> listaVideoVisitas = new ArrayList<VideoConVisitas>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        lvFavoritos = (ListView)findViewById(R.id.lvFavoritos);

        //Rellenar el ArrayList con los vídeos favoritos para mostrarlos en el ListView

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

            for(int i = 0; i<lista.size(); i+=5){
                List sub = lista.subList(i, Math.min(lista.size(), i+5));

                String idVideo = sub.get(0).toString();
                String titulo = sub.get(1).toString();
                String canal = sub.get(2).toString();
                int visitas = Integer.parseInt(sub.get(3).toString());
                String fechaSubida = sub.get(4).toString();

                VideoConVisitas videoVisitas = new VideoConVisitas(idVideo, titulo, canal, visitas, fechaSubida);
                listaVideoVisitas.add(videoVisitas);

                /*
                videoConVisitas.setIdVideo(sub.get(0).toString());
                videoConVisitas.setTitulo(sub.get(1).toString());
                videoConVisitas.setNombreCanal(sub.get(2).toString());
                videoConVisitas.setVisitas(Integer.parseInt(sub.get(3).toString()));
                videoConVisitas.setDiaSubida(sub.get(4).toString());

                listaVideoVisitas.add(videoConVisitas);
                */
            }

            listaVideoVisitas.size();

        }catch (Exception e) {
            e.printStackTrace();
        }

        AdapatadorListView miAdaptador=new AdapatadorListView(getApplicationContext(), listaVideoVisitas);

        lvFavoritos.setAdapter(miAdaptador);

        lvFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                VideoConVisitas obj = (VideoConVisitas) parent.getItemAtPosition(position);

                Intent paso = new Intent(getApplicationContext(), ActivityReproductorVideos.class);
                paso.putExtra("idVideo", listaVideoVisitas.get(position).getIdVideo());
                startActivity(paso);
            }
        });
    }
}
