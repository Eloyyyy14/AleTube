package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

//region Imports

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import android.os.StrictMode;
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

//endregion

public class ActivityReproductorVideos extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener{

//region Creacion Variables

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

    //ArrayList para guardar info de vídeo, ya que con el ArrayList de los objetos de la clase no funciona (No tiene sentido)
    ArrayList<String> listaIdVideo = new ArrayList<String>();
    ArrayList<String> listaTitulo = new ArrayList<String>();
    ArrayList<String> listaNombreCanal = new ArrayList<String>();
    ArrayList<String> listaDiaSubida = new ArrayList<String>();

    //Para configurar vídeos con un máximo de visitas
    int visitasSoloMayor = 0;
    //De inicio saca vídeos entre 0-1000 millones
    int visitasMenor = 0;
    int visitasMayor = 1000000000;
    //Título del vídeo a buscar
    String texto="";

    //Boolean para por si se busca entre dos numeros
    boolean entreDosNumeros = true;
    //Boolean para por si se busca numero max
    boolean numeroMax = false;
    //Boolean por si se busca texto
    boolean textoEscrito = false;

//endregion

//region OnCreate
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

        //Compruebo si vengo de la activity Favoritos y recupero el id del video pulsado

        Intent intent = getIntent();
        String idVideo = intent.getStringExtra("idVideo");

        //Si no es nulo es que venimos de esa activiy
        if(idVideo != null){
            String urlBuscarVideo="https://www.googleapis.com/youtube/v3/search?part=id,snippet&maxResults=1&type=video&q="+idVideo+"&key="+claveYT;
            sacarJsonInfoVideo(urlBuscarVideo);
        }

        else {
            siguienteVideo(v);
        }
    }

//endregion

//region Métodos Relacionados Con El Menú

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
        final EditText etTexto =(EditText) dialogView.findViewById(R.id.etTexto);
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
                if(etMaximo.getText().length() != 0 && etMaximo2.getText().length() == 0 && etMinimo.getText().length() == 0 && etTexto.getText().length() == 0){
                    String mayor = etMaximo.getText().toString();
                    visitasSoloMayor = Integer.parseInt(mayor);

                    entreDosNumeros = false;
                    numeroMax = true;

                    siguienteVideo(v);
                    Toast.makeText(getApplicationContext(), "Configuración: Número máximo guardada", Toast.LENGTH_LONG).show();
                    dialog1.dismiss();
                }
                //Si se escribe entre dos num de visitas(OPCION BUENA)!!!!!!
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() != 0 && etMinimo.getText().length() != 0 && etTexto.getText().length() == 0){
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

                        entreDosNumeros = true;
                        numeroMax = false;

                        siguienteVideo(v);
                        Toast.makeText(getApplicationContext(), "Configuración: Entre dos números guardada", Toast.LENGTH_LONG).show();
                        dialog1.dismiss();
                    }
                }
                //Si solo se escribe texto a buscar(OPCION BUENA)!!!!!!
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() == 0 && etMinimo.getText().length() == 0 && etTexto.getText().length() != 0){
                    texto = etTexto.getText().toString();

                    textoEscrito = true;
                    entreDosNumeros = false;
                    numeroMax = false;

                    System.out.println(texto);
                    siguienteVideo(v);
                    Toast.makeText(getApplicationContext(), "Configuración: Buscar Titulo guardada", Toast.LENGTH_LONG).show();
                    dialog1.dismiss();
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
                else if(etMaximo.getText().length() == 0 && etMaximo2.getText().length() == 0 && etMinimo.getText().length() == 0 && etTexto.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "No hay datos", Toast.LENGTH_LONG).show();
                }
                //Si se escribe en todos
                else if(etMaximo.getText().length() != 0 && etMaximo2.getText().length() != 0 && etMinimo.getText().length() != 0 && etTexto.getText().length() != 0){
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

//endregion

//region Método Sacar Valores Aleatorios

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

//endregion

//region Método Sacar Información De Vídeos

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

                    sacarJsonStats(urlStatsVideo, idVideos, 0);

                } catch (Exception e) {
                    siguienteVideo(v);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                siguienteVideo(v);
            }
        });

        request.add(jsonObjectRequest);
        listVideo.clear();
    }


//endregion

//region Método Sacar Informacion De Vídeos Si Se Busca Por Título

    public void sacarJsonInfoVideoTitulo(String url){

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
                        listaIdVideo.add(idVideo);

                        String tituloVideo=jsonArray.getJSONObject(i).getJSONObject("snippet").getString("title");
                        video.setTitulo(tituloVideo);
                        listaTitulo.add(tituloVideo);

                        String fechaSubida=jsonArray.getJSONObject(i).getJSONObject("snippet").getString("publishedAt");
                        String FfechaSubida=fechaSubida.substring(0, 10);

                        //Fecha bonita
                        String fechaAnno = FfechaSubida.substring(0, 4);
                        String fechaMes = FfechaSubida.substring(5, 7);
                        String fechaDia = FfechaSubida.substring(8, 10);

                        String fechaFinal= fechaDia + "/" + fechaMes + "/" + fechaAnno;

                        video.setDiaSubida(fechaFinal);
                        listaDiaSubida.add(fechaFinal);

                        String nombreCanal=jsonArray.getJSONObject(i).getJSONObject("snippet").getString("channelTitle");
                        video.setNombreCanal(nombreCanal);
                        listaNombreCanal.add(nombreCanal);

                        String miniatura=jsonArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default").getString("url");
                        video.setMiniatura(miniatura);

                        listVideo.add(video);
                    }
                    int a = listVideo.size();
                    int b = listaTitulo.size();
                    int c = listaNombreCanal.size();
                    int d = listaIdVideo.size();
                    int e = listaDiaSubida.size();

                    int indiceAleatorio = new Random().nextInt(listaIdVideo.size());
                    String idVideos=listaIdVideo.get(indiceAleatorio);

                    System.out.println("Id a reproducir YA(sacarJsonInfoVideoTitulo): "+ idVideos);

                    String urlStatsVideo="https://www.googleapis.com/youtube/v3/videos?part=statistics&id="+idVideos+"&key="+claveYT;

                    sacarJsonStats(urlStatsVideo, idVideos, indiceAleatorio);

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

//endregion

//region Método Sacar Visitas Del Vídeo

    public void sacarJsonStats(String url, final String idVideo, final int indice){

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

                        String visitasS = statsVideo.getVisitas();
                        visitas=Integer.parseInt(visitasS);
                        System.out.println("Numero de visitas video dentro bucle(sacarJsonStats): " + visitas);

                    } catch (Exception e) {
                        siguienteVideo(v);
                    }

                //Si el filtro es entre dos números
                if(entreDosNumeros == true && numeroMax == false) {
                    if (visitas <= visitasMayor && visitas >= visitasMenor) {
                        mostrarDatos();
                        youTubePlayer1.loadVideo(idVideo);
                        youTubePlayer1.play();
                        video.setIdVideo(idVideo);
                        System.out.println("Numero de visitas video salida bucle(sacarJsonStats) dos números y no se ha escrito texto: " + visitas);
                    } else {
                        siguienteVideo(v);
                    }
                }
                //Si el filtro es numero max y no se ha escrito texto
                else if (entreDosNumeros == false && numeroMax == true){
                    if (visitas <= visitasSoloMayor) {
                        mostrarDatos();
                        youTubePlayer1.loadVideo(idVideo);
                        youTubePlayer1.play();
                        video.setIdVideo(idVideo);
                        System.out.println("Numero de visitas video salida bucle(sacarJsonStats) numero max y no se ha escrito texto: " + visitas);
                    }
                    else {
                        siguienteVideo(v);
                    }
                }
                //Si el filtro es solo texto
                else if(entreDosNumeros == false  && numeroMax == false && textoEscrito == true){
                    mostrarDatosTexto(indice);
                    youTubePlayer1.loadVideo(idVideo);
                    youTubePlayer1.play();
                    video.setIdVideo(idVideo);
                    System.out.println("Numero de visitas video salida bucle(sacarJsonStats)solo texto: " + visitas);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.add(jsonObjectRequest);
        listStatsVideo.clear();
    }

//endregion

//region Método Mostrar Datos En Los TextView

    public void mostrarDatos(){
        tvNombreVideo.setText(listVideo.get(0).getTitulo());
        tvNombreCanal.setText(listVideo.get(0).getNombreCanal());
        tvFechaSubida.setText(listVideo.get(0).getDiaSubida());

        tvVisitas.setText(listStatsVideo.get(0).getVisitas() + " visitas");
    }

//endregion

//region Método Mostrar Datos En Los TextView Si Se Busca Por Texto

    public void mostrarDatosTexto(int indice){
        tvNombreVideo.setText(listaTitulo.get(indice));
        tvNombreCanal.setText(listaNombreCanal.get(indice));
        tvFechaSubida.setText(listaDiaSubida.get(indice));

        tvVisitas.setText(listStatsVideo.get(0).getVisitas() + " visitas");
    }

//endregion

//region Método Pulsar El Botón De SiguienteVídeo

    public void siguienteVideo(View v){

        if(texto.length() != 0){

            String urlBuscarVideo="https://www.googleapis.com/youtube/v3/search?part=id,snippet&maxResults=50&type=video&q="+texto+"&key="+claveYT;
            sacarJsonInfoVideoTitulo(urlBuscarVideo);
            System.out.println("aaaaaa "+urlBuscarVideo);
        }

        else {
            String idVideo=obtenerRandomIdVideo();

            String urlBuscarVideo="https://www.googleapis.com/youtube/v3/search?part=id,snippet&maxResults=5&type=video&q="+idVideo+"&key="+claveYT;
            sacarJsonInfoVideo(urlBuscarVideo);
        }

        if(entreDosNumeros == false  && numeroMax == false && textoEscrito == true){
            if(listaIdVideo.size() == 0){
                String urlBuscarVideo="https://www.googleapis.com/youtube/v3/search?part=id,snippet&maxResults=50&type=video&q="+texto+"&key="+claveYT;
                sacarJsonInfoVideoTitulo(urlBuscarVideo);
            }
        }

        Toast.makeText(getApplicationContext(), "Buscando vídeo para reproducir... Puede tardar, dependiendo de los criterios de búsqueda", Toast.LENGTH_LONG).show();
    }

//endregion

//region Método Pulsar El Botón De Compartir

    public void compartir(View v){
        String id = video.getIdVideo();
        String link = "https://www.youtube.com/watch?v=" + id;

        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "Mira este vídeo de AleTube!");
        share.putExtra(Intent.EXTRA_TEXT, "Mira este vídeo de AleTube! " + link);
        startActivity(Intent.createChooser(share, "Compartir vía"));
    }

//endregion

//region Método Pulsar El Botón De Favorito

    public void favorito(View v){

    }

//endregion

//region Métodos De La API De YouTube

    //Método para comprobar si fue bien
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        //Si fue bien entonces carga y reproduce el video
        if(!b){
            youTubePlayer1=youTubePlayer;
            youTubePlayer1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            //Al girar el móvil se pone auto en pantalla completa
            youTubePlayer1.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
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

    //Pendiente esto
    @Override
    public void onFullscreen(boolean b) {

    }

//endregion
}
