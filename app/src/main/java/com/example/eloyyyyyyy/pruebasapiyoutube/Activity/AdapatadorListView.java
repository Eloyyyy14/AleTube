package com.example.eloyyyyyyy.pruebasapiyoutube.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eloyyyyyyy.pruebasapiyoutube.Clases.VideoConVisitas;
import com.example.eloyyyyyyy.pruebasapiyoutube.R;

import java.util.ArrayList;

public class AdapatadorListView extends BaseAdapter{
    Context contexto; //contexto de la aplicacion
    ArrayList<VideoConVisitas> videoConVisitas;

    public AdapatadorListView(Context contexto, ArrayList<VideoConVisitas> datos) {
        this.contexto = contexto;
        videoConVisitas = datos;
    }

    @Override
    public int getCount() {
        return videoConVisitas.size();
    }

    @Override
    public Object getItem(int position) {
        return videoConVisitas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return videoConVisitas.get(position).getVisitas();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista=convertView;
        LayoutInflater inflate = LayoutInflater.from(contexto); //Obtenemos el contexto del item sobre el cual estamos trabajando del ListView
        vista=inflate.inflate(R.layout.itemlistview, null); //Consigo referenciar a la vista en sí

        TextView tvTitulo=(TextView)vista.findViewById(R.id.tvTitulo);
        TextView tvCanal=(TextView)vista.findViewById(R.id.tvCanal);


        tvTitulo.setText(videoConVisitas.get(position).getTitulo());
        tvCanal.setText(videoConVisitas.get(position).getNombreCanal());

        return vista;
    }
}
