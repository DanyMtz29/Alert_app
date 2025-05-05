package com.example.alert_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.telephony.SmsManager;

import com.example.DataBase.Controller;

public class Principal extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private Handler handler = new Handler();
    private boolean isLongPress = false;
    private Button botonAlerta; // Tu botón de alerta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        ImageButton menuButton = findViewById(R.id.imageButton3);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Principal.this, v);
                popup.getMenuInflater().inflate(R.menu.drawer_menu, popup.getMenu());

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
                            i = new Intent("android.media.action.IMAGE_CAPTURE");
                            startActivity(i);
                        } else if (id == R.id.menu_terminos) {
                            Toast.makeText(Principal.this, "Términos y Condiciones seleccionados", Toast.LENGTH_SHORT).show();
                            i = new Intent(Principal.this, termsConditionsActivity.class);
                            startActivity(i);
                        } else if (id == R.id.menu_aviso_privacidad) {
                            Toast.makeText(Principal.this, "Aviso de Privacidad seleccionado", Toast.LENGTH_SHORT).show();
                            i = new Intent(Principal.this, privacyNoticeActivity.class);
                            startActivity(i);
                        } else {
                            return false;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        // 🛡️ Configuramos el botón de alerta
        botonAlerta = findViewById(R.id.btnAlerta);
        configurarBotonAlerta(botonAlerta);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void configurarBotonAlerta(Button boton) {
        boton.setOnTouchListener(new View.OnTouchListener() {
            private long tiempoInicio;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tiempoInicio = System.currentTimeMillis();
                        handler.postDelayed(() -> {
                            if (System.currentTimeMillis() - tiempoInicio >= 3000) { // 3 segundos
                                isLongPress = true;
                                activarAlerta();
                            }
                        }, 3000);
                        return true;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacksAndMessages(null);
                        if (!isLongPress) {
                            Toast.makeText(Principal.this, "Mantén presionado 3 segundos para activar alerta", Toast.LENGTH_SHORT).show();
                        }
                        isLongPress = false;
                        return true;
                }
                return false;
            }
        });
    }

    private void activarAlerta() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        String mensaje = "EMERGENCIA \nUbicación aproximada:\nLat: " + lat + "\nLon: " + lon;
                        llamarSeguridadPublica();

                        //simulacion
                        Toast.makeText(Principal.this, mensaje, Toast.LENGTH_LONG).show();

                        // mandar SMS real:
                    } else {
                        Toast.makeText(Principal.this, "No se pudo obtener ubicación", Toast.LENGTH_SHORT).show();
                    }


                });


    }

    private void enviarSmsEmergencia(String numero, String mensaje) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2);
            return;
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero, null, mensaje, null, null);
    }

    private void llamarSeguridadPublica() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:8717471524"));
        startActivity(intent);
    }

    public void llamarSeguridadPublica(View v) {
        llamarSeguridadPublica();
    }

    public void llamar911(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 4);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:911"));
        startActivity(intent);

    }

}
