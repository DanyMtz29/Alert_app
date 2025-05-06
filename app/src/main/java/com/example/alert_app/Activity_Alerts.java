package com.example.alert_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.DataBase.Alert;
import com.example.DataBase.Controller;

import java.util.ArrayList;

public class Activity_Alerts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlertaAdapter adapter;
    private ArrayList<Alerta> listaAlertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        recyclerView = findViewById(R.id.recyclerAlertas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarAlertasDesdeBD();
    }

    private void cargarAlertasDesdeBD() {
        Controller controller = new Controller(this);
        listaAlertas = new ArrayList<>();

        // Leemos todas las alertas de la BD
        for (Alerta a : controller.readAllAlerts(1)) { // Suponiendo que el usuario con id = 1
            String ubicacion = a.getUbicacion();
            String fecha = a.getFecha(); // Ya formateada
            String foto = a.getRutaFoto();

            listaAlertas.add(new Alerta(fecha, ubicacion, foto));
        }

        adapter = new AlertaAdapter(this,listaAlertas);
        recyclerView.setAdapter(adapter);
    }
}
