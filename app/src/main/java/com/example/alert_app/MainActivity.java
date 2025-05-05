package com.example.alert_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.DataBase.Controller;
import com.example.DataBase.getImageUbi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    Controller controller;

    EditText bxUName, bxULastName, bxUAdrCity, bxUAdrColony, bxUAdrStreet, bxUAdrNumber, bxUAdrCp, bxUNumberTel;
    EditText bxC1Name, bxC1LastName, bxC1Number, bxC1Relation, bxC2Name, bxC2LastName, bxC2Number, bxC2Relation;
    ImageButton imgProfile;
    Button btnRegister;

    String imgPath = "";

    FusedLocationProviderClient fusedLocationClient;
    ActivityResultLauncher<Intent> imageResultLauncher;
    CheckBox ckBox;

    File archivoFotoPerfil;
    Uri[] uriFoto = new Uri[1];
    int currentRequestCode = -1;
    Bitmap fotoTemporalBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        controller = new Controller(this);

        bxUName = findViewById(R.id.boxUserName);
        bxULastName = findViewById(R.id.boxUserLastName);
        bxUAdrCity = findViewById(R.id.boxAddressCity);
        bxUAdrColony = findViewById(R.id.boxAddressColony);
        bxUAdrStreet = findViewById(R.id.boxAddressStreet);
        bxUAdrNumber = findViewById(R.id.boxAddressNumber);
        bxUAdrCp = findViewById(R.id.boxAddressCP);
        bxUNumberTel = findViewById(R.id.boxNumberTel);

        bxC1Name = findViewById(R.id.boxContact1Name);
        bxC1LastName = findViewById(R.id.boxContact1LastName);
        bxC1Number = findViewById(R.id.boxContact1Number);
        bxC1Relation = findViewById(R.id.boxContact1Relation);
        bxC2Name = findViewById(R.id.boxContact2Name);
        bxC2LastName = findViewById(R.id.boxContact2LastName);
        bxC2Number = findViewById(R.id.boxContact2Number);
        bxC2Relation = findViewById(R.id.boxContact2Relation);
        ckBox = findViewById(R.id.checkBox);

        imgProfile = findViewById(R.id.imgProfileButton);
        btnRegister = findViewById(R.id.buttonRegister);

        imgProfile.setOnClickListener(v -> {
            String[] opciones = {"Tomar Foto", "Elegir de Galería"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona una opción")
                    .setItems(opciones, (dialog, which) -> {
                        Intent intent;
                        if (which == 0) {
                            archivoFotoPerfil = new File(getFilesDir(), "foto_temp.jpg");
                            intent = getImageUbi.obtenerIntentCamara(this, archivoFotoPerfil, uriFoto);
                        } else {
                            intent = getImageUbi.obtenerIntentGaleria();
                        }
                        currentRequestCode = (which == 0) ? REQUEST_CAMERA : REQUEST_GALLERY;
                        imageResultLauncher.launch(intent);
                    }).show();
        });

        imageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        fotoTemporalBitmap = getImageUbi.obtenerBitmapDesdeResultado(this, currentRequestCode, uriFoto[0], result.getData());
                        if (fotoTemporalBitmap != null) {
                            imgProfile.setImageBitmap(fotoTemporalBitmap);
                            Toast.makeText(this, "Imagen lista para guardar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(View v) {
        if (check()) {
            Toast.makeText(this, "Son obligatorios todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ckBox.isChecked()) {
            Toast.makeText(this, "Se deben de aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
            return;
        }

        File archivoFinal = new File(getFilesDir(), "foto_perfil.jpg");
        if (fotoTemporalBitmap != null) {
            try (FileOutputStream out = new FileOutputStream(archivoFinal)) {
                fotoTemporalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                imgPath = archivoFinal.getAbsolutePath();

                File archivoTemp = new File(getFilesDir(), "foto_temp.jpg");
                if (archivoTemp.exists()) archivoTemp.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long id_address = controller.insertAddress(
                bxUAdrCity.getText().toString(),
                bxUAdrColony.getText().toString(),
                bxUAdrStreet.getText().toString(),
                Integer.parseInt(bxUAdrNumber.getText().toString()),
                Integer.parseInt(bxUAdrCp.getText().toString())
        );

        if (id_address != -1) {
            long id_user = controller.insertUser(
                    bxUName.getText().toString(),
                    bxULastName.getText().toString(),
                    id_address,
                    bxUNumberTel.getText().toString(),
                    imgPath
            );

            if (id_user != -1) {
                long c1 = controller.insertContact(
                        bxC1Name.getText().toString(),
                        bxC1LastName.getText().toString(),
                        bxC1Number.getText().toString(),
                        bxC1Relation.getText().toString(),
                        id_user
                );
                long c2 = controller.insertContact(
                        bxC2Name.getText().toString(),
                        bxC2LastName.getText().toString(),
                        bxC2Number.getText().toString(),
                        bxC2Relation.getText().toString(),
                        id_user
                );
                if (c1 != -1 && c2 != -1) {
                    Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_LONG).show();
                    SharedPreferences prefs = getSharedPreferences("registro", MODE_PRIVATE);
                    prefs.edit().putBoolean("yaRegistrado", true).apply();
                    startActivity(new Intent(this, Principal.class));
                    finish();
                } else {
                    Toast.makeText(this, "Error al guardar los contactos", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean check() {
        return bxUName.getText().toString().isEmpty() ||
                bxULastName.getText().toString().isEmpty() ||
                bxUAdrCity.getText().toString().isEmpty() ||
                bxUAdrStreet.getText().toString().isEmpty() ||
                bxUAdrNumber.getText().toString().isEmpty() ||
                bxUAdrCp.getText().toString().isEmpty() ||
                bxUNumberTel.getText().toString().isEmpty() ||
                bxC1Name.getText().toString().isEmpty() ||
                bxC1LastName.getText().toString().isEmpty() ||
                bxC1Number.getText().toString().isEmpty() ||
                bxC1Relation.getText().toString().isEmpty() ||
                bxC2Name.getText().toString().isEmpty() ||
                bxC2LastName.getText().toString().isEmpty() ||
                bxC2Number.getText().toString().isEmpty() ||
                bxC2Relation.getText().toString().isEmpty();
    }
}
