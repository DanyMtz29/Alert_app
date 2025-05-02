package com.example.alert_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class centerInformationACtivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcherLlamada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_information);

        // Preparamos el launcher para detectar cuando regresan del marcador
        launcherLlamada = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    volverAlCentro();
                }
        );
    }

    private void centro1() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:8712226315"));
        startActivity(intent);
    }


    private void centro2() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:8715007419"));
        startActivity(intent);
    }

    public void mapa1(View v){
        String nombreLugar = "Centro de Justicia para las Mujeres, Blvd. Francisco Sarabia, San Felipe, 27085 Torreón, Coah.";
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(nombreLugar));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // abre Google Maps si existe

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Si no tiene Google Maps, abrir en navegador
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(nombreLugar)));
            startActivity(browserIntent);
        }
    }

    public void mapa2(View v){
        String nombreLugar = "Instituto Municipal de la Mujer de Torreón, Prolongación Colón y Calle Delicias Col, Luis Echeverría Álvarez, 27220 Torreón, Coah.";

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(nombreLugar));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // abre Google Maps si existe

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Si no tiene Google Maps, abrir en navegador
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(nombreLugar)));
            startActivity(browserIntent);
        }
    }

    public void backCenter(View v){
        Intent i = new Intent(centerInformationACtivity.this, informationActivity.class);
        startActivity(i);
        this.finish();
    }

    private void volverAlCentro() {
        Intent i = new Intent(this, centerInformationACtivity.class);
        startActivity(i);
        finish(); // Cerramos esta activity
    }
}
