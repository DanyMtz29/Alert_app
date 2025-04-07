package com.example.alert_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Activar EdgeToEdge si lo deseas
        // EdgeToEdge.enable(this);  // Descomenta si ya tienes configurado EdgeToEdge
        setContentView(R.layout.principal);

        ImageButton menuButton = findViewById(R.id.imageButton3);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Crear el PopupMenu anclado al botón
                PopupMenu popup = new PopupMenu(Principal.this, v);
                popup.getMenuInflater().inflate(R.menu.drawer_menu, popup.getMenu());

                // Configurar el listener usando if-else para cada opción
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                Intent i;
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menu_informacion) {
                            Toast.makeText(Principal.this, "Información seleccionada", Toast.LENGTH_SHORT).show();
                            i = new Intent(Principal.this, informationActivity.class);
                            startActivity(i);
                        } else if (id == R.id.menu_configuracion) {
                            Toast.makeText(Principal.this, "Configuración seleccionada", Toast.LENGTH_SHORT).show();
                            i = new Intent(Principal.this, configurationActivity.class);
                            startActivity(i);
                        } else if (id == R.id.menu_alertas_emitidas) {
                            Toast.makeText(Principal.this, "Alertas Emitidas seleccionadas", Toast.LENGTH_SHORT).show();
                        } else if (id == R.id.menu_tomar_foto) {
                            Toast.makeText(Principal.this, "Tomar Foto seleccionada", Toast.LENGTH_SHORT).show();
                        } else if (id == R.id.menu_terminos) {
                            Toast.makeText(Principal.this, "Términos y Condiciones seleccionados", Toast.LENGTH_SHORT).show();
                        } else if (id == R.id.menu_aviso_privacidad) {
                            Toast.makeText(Principal.this, "Aviso de Privacidad seleccionado", Toast.LENGTH_SHORT).show();
                        } else {
                            return false;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }
}
