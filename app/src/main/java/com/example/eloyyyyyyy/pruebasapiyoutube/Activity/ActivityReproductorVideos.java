package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eloyyyyyyy.pruebasapiyoutube.R;
import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.StatsVideo;
import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class ActivityReproductorVideos extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener{

    Button siguienteVideo, favorito, btnCompartir;
    TextView tvNombreVideo, tvNombreCanal, tvFechaSubida, tvVisitas;
    View v;

    private String claveYT="AIzaSyD1ykwAYUodC9hA_kUrRRj7oCJXk8iPSYM";
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer1;
    //private String idVideo="DRS_PpOrUZ4"; //https://www.youtube.com/watch?v=azxDhcKYku4
    //private String urlInfoVideo="https://www.googleapis.com/youtube/v3/videos?part=id%2Csnippet&id="+idVideo+"&key="+claveYT;
    //private String urlStatsVideo="https://www.googleapis.com/youtube/v3/videos?part=statistics&id="+idVideo+"&key="+claveYT;
    ArrayList<Video> listVideo = new ArrayList<Video>();
    ArrayList<StatsVideo> listStatsVideo = new ArrayList<StatsVideo>();
    Video video=new Video();
    StatsVideo statsVideo = new StatsVideo();

    //Para configurar vídeos con un máximo de visitas
    int visitasMayorMayor = 0;
    //De inicio saca vídeos entre 0-1000 millones
    int visitasMenor = 0;
    int visitasMayor = 1000000000;

    boolean entreDosNumeros = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_videos);

        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(claveYT, this);
        siguienteVideo=(Button)findViewById(R.id.btnSiguienteVideo);
        favorito=(Button)findViewById(R.id.btnFavoritos);
        btnCompartir=(Button)findViewById(R.id.btnCompartir);
        tvNombreVideo=(TextView)findViewById(R.id.tvNombreVideo);
        tvNombreCanal=(TextView)findViewById(R.id.tvNombreCanal);
        tvFechaSubida=(TextView)findViewById(R.id.tvFecha);
        tvVisitas=(TextView)findViewById(R.id.tvVisitas);

        siguienteVideo(v);
    }

    //Metodo del menu crear
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reproductor, menu);
        return true;
    }

    //Método del menú click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final AlertDialog.Builder dialog =  new AlertDialog.Builder(ActivityReproductorVideos.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_visitas, null);
        TextView tvInicio = (TextView) dialogView.findViewById(R.id.tvInicio);
        TextView tvPrimero = (TextView) dialogView.findViewById(R.id.tvPrimero);
        TextView tvSegundo = (TextView) dialogView.findViewById(R.id.tvSegundo);
        final EditText etMaximo = (EditText) dialogView.findViewById(R.id.etMaximo);
        final EditText etMaximo2 = (EditText) dialogView.findViewById(R.id.etMaximo2);
        final EditText etMinimo = (EditText) dialogView.findViewById(R.id.etMinimo);
        Button btnCancelar = (Button) dialogView.findViewById(R.id.btnCancelar);
        Button btnGuardar = (Button) dialogView.findViewById(R.id.btnGuardar);

        dialog.setView(dialogView);
        final AlertDialog dialog1 = dialog.create();
        dialog1.show();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si solo se escribe en el max(OPCION BUENA)!!!!!!
                if(etMaximo.getText().length() != 0 && etMaximo2.getText().length() == 0 && etMinimo.getText().length() == 0){
                    String mayor = etMaximo.getText().toString();

                    visitasMayorMayor = Integer.parseInt(mayor);

                    siguienteVideo(v);
                    entreDosNumeros=false;
                    Toast.makeText(getApplicationContext(), "Configuración: Número máximo guardada", Toast.LENGTH_LONG).show();
                    dialog1.dismiss();
                }
                //Si se escribe entre dos num de visitas(OPCION BUENA)!!!!!!
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() != 0 && etMinimo.getText().length() != 0){
                    String mayor = etMaximo2.getText().toString();
                    String menor = etMinimo.getText().toString();

                    int may = Integer.parseInt(mayor);
                    int men = Integer.parseInt(menor);

                    if(men>may){
                        Toast.makeText(getApplicationContext(), "El menor no puede ser más grande que el mayor", Toast.LENGTH_LONG).show();
                    }

                    else if(may==men){
                        Toast.makeText(getApplicationContext(), "No pueden ser dos números iguales", Toast.LENGTH_LONG).show();
                    }

                    else {
                        visitasMayor = Integer.parseInt(mayor);
                        visitasMenor = Integer.parseInt(menor);

                        siguienteVideo(v);
                        entreDosNumeros = true;
                        Toast.makeText(getApplicationContext(), "Configuración: Entre dos números guardada", Toast.LENGTH_LONG).show();
                        dialog1.dismiss();
                    }
                }
                //Si se escribe en max y en max2
                else if(etMaximo.getText().length() != 0 && etMaximo2.getText().length() != 0 & etMinimo.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Escriba un número máximo o entre dos números", Toast.LENGTH_LONG).show();
                }
                //Si se escribe en max y en min
                else if(etMaximo.getText().length() != 0 && etMaximo2.getText().length() == 0 & etMinimo.getText().length() != 0){
                    Toast.makeText(getApplicationContext(), "Escriba un número máximo o entre dos números", Toast.LENGTH_LONG).show();
                }
                //Si no se escribe en ninguno
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() == 0 && etMinimo.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "No hay datos", Toast.LENGTH_LONG).show();
                }
                //Si se escribe en todos
                else if(etMaximo.getText().length() != 0 && etMaximo2.getText().length() != 0 && etMinimo.getText().length() != 0){
                    Toast.makeText(getApplicationContext(), "No escriba en todos", Toast.LENGTH_LONG).show();
                }
                //Si solo se escribe en el max2
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() != 0 && etMinimo.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Escriba un número máximo o entre dos números", Toast.LENGTH_LONG).show();
                }
                //Si solo se escribe en el min
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() == 0 && etMinimo.getText().length() != 0){
                    Toast.makeText(getApplicationContext(), "Escriba un número máximo o entre dos números", Toast.LENGTH_LONG).show();
                }
            }
        });

        return true;
    }

//-----------------------------------------------------------------------------------------------------------------------------
//Mis Metodos

    //Saco 5 caracteres aleatorios para hacer una búsqueda de vídeos que contengan un id de video con esos caracteres
    public String obtenerRandomIdVideo(){
        String[] listLetras={"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "_", "-"};

        String a="";
        String b="";
        String c="";
        String d="";
        String e="";

        int rnd = new Random().nextInt(listLetras.length);
        a=listLetras[rnd];

        int rnd1 = new Random().nextInt(listLetras.length);
        b=listLetras[rnd1];

        int rnd2 = new Random().nextInt(listLetras.length);
        c=listLetras[rnd2];

        int rnd3 = new Random().nextInt(listLetras.length);
        d=listLetras[rnd3];

        int rnd4 = new Random().nextInt(listLetras.length);
        e=listLetras[rnd4];

        String id= a + b +  c +  d + e;

        System.out.println("String a buscar(obtenerRandomIdVideo): "+id);

        return id;
    }

    //Sacar datos de video del json
    public void sacarJsonInfoVideo(String url){

        RequestQueue request = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString(0));

                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0; i<jsonArray.length(); i++){
                        String idVideo=jsonArray.getJSONObject(i).getJSONObject("id").getString("videoId");
                        video.setIdVideo(idVideo);

                        String tituloVideo=jsonArray.getJSONObject(i).getJSONObject("snippet").getString("title");
                        video.setTitulo(tituloVideo);

                        String fechaSubida=jsonArray.getJSONObject(i).getJSONObject("snippet").getString("publishedAt");
                        String FfechaSubida=fechaSubida.substring(0, 10);

                        //Fecha bonita
                        String fechaAnno = FfechaSubida.substring(0, 4);
                        String fechaMes = FfechaSubida.substring(5, 7);
                        String fechaDia = FfechaSubida.substring(8, 10);

                        String fechaFinal= fechaDia + "/" + fechaMes + "/" + fechaAnno;

                        video.setDiaSubida(fechaFinal);

                        String nombreCanal=jsonArray.getJSONObject(i).getJSONObject("snippet").getString("channelTitle");
                        video.setNombreCanal(nombreCanal);

                        String miniatura=jsonArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                        video.setMiniatura(miniatura);

                        listVideo.add(video);
                    }

                    String idVideos = listVideo.get(0).getIdVideo();
                    System.out.println("Id a reproducir YA(sacarJsonInfoVideo): "+ idVideos);

                    String urlStatsVideo="https://www.googleapis.com/youtube/v3/videos?part=statistics&id="+idVideos+"&key="+claveYT;

                    sacarJsonStats(urlStatsVideo, idVideos);

                    /*
                    tvNombreVideo.setText(listVideo.get(0).getTitulo());
                    tvNombreCanal.setText(listVideo.get(0).getNombreCanal());
                    tvFechaSubida.setText(listVideo.get(0).getDiaSubida());
                    */
                } catch (Exception e) {
                    siguienteVideo(v);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.add(jsonObjectRequest);
    }

    //Sacar datos de estadisticas json a partir de una url y una idVideo para reproducir
    public void sacarJsonStats(String url, final String idVideo){
        RequestQueue request = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int visitas=0;
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString(0));

                        JSONArray jsonArray = jsonObject.getJSONArray("items");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String numVisitas = jsonArray.getJSONObject(i).getJSONObject("statistics").getString("viewCount");
                            statsVideo.setVisitas(numVisitas);

                            listStatsVideo.add(statsVideo);
                        }

                        //tvVisitas.setText(listStatsVideo.get(0).getVisitas() + " visitas");

                        String visitasS = statsVideo.getVisitas();
                        visitas=Integer.parseInt(visitasS);
                        System.out.println("Numero de visitas video dentro bucle(sacarJsonStats): " + visitas);

                    } catch (Exception e) {
                        siguienteVideo(v);
                    }

                if(entreDosNumeros==true) {
                    if (visitas <= visitasMayor && visitas >= visitasMenor) {
                        mostrarDatos();
                        youTubePlayer1.loadVideo(idVideo);
                        youTubePlayer1.play();
                        video.setIdVideo(idVideo);
                        System.out.println("Numero de visitas video salida bucle(sacarJsonStats): " + visitas);
                    } else {
                        siguienteVideo(v);
                    }
                }
                else{
                    if (visitas <= visitasMayorMayor) {
                        mostrarDatos();
                        youTubePlayer1.loadVideo(idVideo);
                        youTubePlayer1.play();
                        video.setIdVideo(idVideo);
                        System.out.println("Numero de visitas video salida bucle(sacarJsonStats): " + visitas);
                    } else {
                        siguienteVideo(v);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.add(jsonObjectRequest);
    }

    public void mostrarDatos(){
        tvNombreVideo.setText(listVideo.get(0).getTitulo());
        tvNombreCanal.setText(listVideo.get(0).getNombreCanal());
        tvFechaSubida.setText(listVideo.get(0).getDiaSubida());

        tvVisitas.setText(listStatsVideo.get(0).getVisitas() + " visitas");
    }

    //Metodo al pulsar el botón
    public void siguienteVideo(View v){

        String idVideo=obtenerRandomIdVideo();
        System.out.println("Id URL(siguienteVideo): "+idVideo);

        String urlBuscarVideo="https://www.googleapis.com/youtube/v3/search?part=id,snippet&maxResults=5&type=video&q="+idVideo+"&key="+claveYT;

        sacarJsonInfoVideo(urlBuscarVideo);
        Toast.makeText(getApplicationContext(), "Buscando vídeo para reproducir... Puede demorarse, dependiendo de los criterios de búsqueda", Toast.LENGTH_LONG).show();
    }

    //Metodo pulsar boton
    public void compartir(View v){
        String id = video.getIdVideo();
        String link = "https://www.youtube.com/watch?v=" + id;

        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "Mira este vídeo de AleTube!");
        share.putExtra(Intent.EXTRA_TEXT, "Mira este vídeo de AleTube! " + link);
        startActivity(Intent.createChooser(share, "Compartir vía"));
    }

//Fin Mis Metodos
//------------------------------------------------------------------------------------------------------------

    //Método para comprobar si fue bien
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        //Si fue bien entonces carga y reproduce el video
        if(!b){
            youTubePlayer1=youTubePlayer;
            youTubePlayer1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

            //Carga y reproduce directamente el video

        }
    }

    //Método para comprobar si algo fue mal
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        //Si existió algún error al inicializar muestra un dialog con el error
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        }
        //Si YT no sabe cual es el error enviará este mensaje
        else{
            Toast.makeText(getApplicationContext(), "Error al inializar YouTube "+youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //Si se ha resuelto el error ¿?
        if(resultCode==1){
            getYouTubePlayerView().initialize(claveYT, this);
        }
    }

    public YouTubePlayerView getYouTubePlayerView() {
        return youTubePlayerView;
    }

}
