package com.example.alert_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class AlertaAdapter extends RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder> {

    private ArrayList<Alerta> listaAlertas;
    private Context context;

    public AlertaAdapter(ArrayList<Alerta> listaAlertas, Context context) {
        this.listaAlertas = listaAlertas;
        this.context = context;
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
        holder.txtFecha.setText(alerta.getFecha());
        holder.txtUbicacion.setText(alerta.getUbicacion());

        // 📸 Cargar imagen si existe
        if (alerta.getRutaFoto() != null && !alerta.getRutaFoto().isEmpty()) {
            File imgFile = new File(alerta.getRutaFoto());
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgFoto.setImageBitmap(bitmap);
            } else {
                holder.imgFoto.setImageResource(R.drawable.sin_foto); // fallback
            }
        } else {
            holder.imgFoto.setImageResource(R.drawable.sin_foto); // fallback si no hay foto
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
