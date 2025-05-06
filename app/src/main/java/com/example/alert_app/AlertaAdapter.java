package com.example.alert_app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class AlertaAdapter extends RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder> {

    private Context context;
    private List<Alerta> listaAlertas;

    public AlertaAdapter(Context context, List<Alerta> listaAlertas) {
        this.context = context;
        this.listaAlertas = listaAlertas;
    }

    @NonNull
    @Override
    public AlertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.alerta_item, parent, false);
        return new AlertaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertaViewHolder holder, int position) {
        Alerta alerta = listaAlertas.get(position);
        holder.txtFecha.setText("Fecha: " + alerta.getFecha());
        holder.txtUbicacion.setText("Ubicación: " + alerta.getUbicacion());

        // Si hay una ruta de foto válida
        if (alerta.getRutaFoto() != null && !alerta.getRutaFoto().isEmpty()) {
            File imgFile = new File(alerta.getRutaFoto());
            if (imgFile.exists()) {
                holder.imgFoto.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            } else {
                holder.imgFoto.setImageResource(R.drawable.sin_foto);
            }
        } else {
            holder.imgFoto.setImageResource(R.drawable.sin_foto);
        }
    }

    @Override
    public int getItemCount() {
        return listaAlertas.size();
    }

    public static class AlertaViewHolder extends RecyclerView.ViewHolder {
        TextView txtFecha, txtUbicacion;
        ImageView imgFoto;

        public AlertaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtUbicacion = itemView.findViewById(R.id.txtUbicacion);
            imgFoto = itemView.findViewById(R.id.imgFoto);
        }
    }
}
