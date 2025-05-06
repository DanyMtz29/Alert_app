package com.example.alert_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.DataBase.Controller;
import com.example.DataBase.getImageUbi;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class configurationActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    Controller controller;

    EditText bxUName, bxULastName, bxUAdrCity, bxUAdrColony, bxUAdrStreet, bxUAdrNumber, bxUAdrCp, bxUNumberTel;

    EditText bxC1Name, bxC1LastName, bxC1Number, bxC1Relation,bxC2Name, bxC2LastName, bxC2Number, bxC2Relation;

    ImageButton imgProfile;

    Button btnUpdate;

    String imgPath = "";

    FusedLocationProviderClient fusedLocationClient;
    ActivityResultLauncher<Intent> imageResultLauncher;
    File archivoFotoPerfil;
    Uri[] uriFoto = new Uri[1]; // Almacena la URI generada con FileProvider

    int currentRequestCode = -1;

    Bitmap fotoTemporalBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuration);

        bxUName = (EditText) findViewById(R.id.boxUserName);
        bxULastName = (EditText) findViewById(R.id.boxUserLastName);
        bxUAdrCity = (EditText) findViewById(R.id.boxUserCity);
        bxUAdrColony = (EditText) findViewById(R.id.boxUserColony);
        bxUAdrStreet = (EditText) findViewById(R.id.boxUserStreet);
        bxUAdrNumber = (EditText) findViewById(R.id.boxUserNumberAdr);
        bxUAdrCp = (EditText) findViewById(R.id.boxUserCP);
        bxUNumberTel = (EditText) findViewById(R.id.boxUserNumber);
        bxC1Name = (EditText) findViewById(R.id.boxC1Name);
        bxC1LastName = (EditText) findViewById(R.id.boxC1LastName);
        bxC1Number = (EditText) findViewById(R.id.boxC1Phone);
        bxC1Relation = (EditText) findViewById(R.id.boxC1Relation);
        bxC2Name = (EditText) findViewById(R.id.boxC2Name);
        bxC2LastName = (EditText) findViewById(R.id.boxC2LastName);
        bxC2Number = (EditText) findViewById(R.id.boxC2Phone);
        bxC2Relation = (EditText) findViewById(R.id.boxC2Relation);
        imgProfile = (ImageButton) findViewById(R.id.imgProfileB);
        controller = new Controller(this);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        //controller.resetDatabase(this);//===========================borrar
        cargarDatosDesdeBD();

        imgProfile.setOnClickListener(v -> {
            String[] opciones = {"Tomar Foto", "Elegir de Galería"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona una opción")
                    .setItems(opciones, (dialog, which) -> {
                        Intent intent;
                        if (which == 0) {
                            // CAMARA
                            //archivoFotoPerfil = new File(getFilesDir(), "foto_perfil.jpg");
                            archivoFotoPerfil = new File(getFilesDir(), "foto_temp.jpg");
                            intent = getImageUbi.obtenerIntentCamara(this, archivoFotoPerfil, uriFoto);
                        } else {
                            // GALERIA
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
                            Toast.makeText(this, "Imagen lista, presiona Guardar cambios", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void back(View v){
        Intent i = new Intent(configurationActivity.this, Principal.class);
        startActivity(i);
        finish();
    }

    private void cargarDatosDesdeBD() {

        String[] userData = controller.readUser();
        if (userData[0] != null) {
            bxUName.setText(userData[1]);
            bxULastName.setText(userData[2]);
            bxUNumberTel.setText(userData[3]);
            bxUAdrCity.setText(userData[4]);
            bxUAdrColony.setText(userData[5]);
            bxUAdrStreet.setText(userData[6]);
            bxUAdrNumber.setText(userData[7]);
            bxUAdrCp.setText(userData[8]);

            if (fotoTemporalBitmap == null && userData[9] != null && !userData[9].isEmpty()) {
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(userData[9]));
            }
        }else{
            Toast.makeText(this, "NO HAY DATOS", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = getSharedPreferences("registro", MODE_PRIVATE);
            prefs.edit().putBoolean("yaRegistrado", false).apply();
            return;
        }

        String[] contactos = controller.readContact(Long.parseLong(userData[0])); // ID del usuario
        if (contactos[0] != null) {
            bxC1Name.setText(contactos[0]);
            bxC1LastName.setText(contactos[1]);
            bxC1Number.setText(contactos[2]);
            bxC1Relation.setText(contactos[3]);
        }
        if (contactos[4] != null) {
            bxC2Name.setText(contactos[4]);
            bxC2LastName.setText(contactos[5]);
            bxC2Number.setText(contactos[6]);
            bxC2Relation.setText(contactos[7]);
        }
    }

    public void updateC(View v) {
        String[] userData = controller.readUser();
        if (userData[0] == null) {
            Toast.makeText(this, "No hay datos para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        long id_user = Long.parseLong(userData[0]);
        long id_address = 1;
        long[] contactIds = controller.getContactIds(id_user);  // Debes implementarlo

        if (fotoTemporalBitmap != null) {
            File tempFile = new File(getFilesDir(), "foto_temp.jpg");
            File finalFile = new File(getFilesDir(), "foto_perfil.jpg");

            try (FileOutputStream out = new FileOutputStream(finalFile)) {
                fotoTemporalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                imgPath = finalFile.getAbsolutePath();

                // Elimina la temporal si quieres
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar la nueva imagen", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            imgPath = userData[9];
        }

        // Actualizar datos del usuario
        int resUser = controller.updateUser(
                id_user,
                bxUName.getText().toString(),
                bxULastName.getText().toString(),
                bxUNumberTel.getText().toString(),
                imgPath
        );

        // Actualizar dirección
        int resAddress = controller.updateAddress(
                id_address,
                bxUAdrCity.getText().toString(),
                bxUAdrColony.getText().toString(),
                bxUAdrStreet.getText().toString(),
                Integer.parseInt(bxUAdrNumber.getText().toString()),
                Integer.parseInt(bxUAdrCp.getText().toString())
        );

        // Actualizar contactos
        int resC1 = controller.updateContact(
                contactIds[0],
                bxC1Name.getText().toString(),
                bxC1LastName.getText().toString(),
                bxC1Number.getText().toString(),
                bxC1Relation.getText().toString()
        );

        int resC2 = controller.updateContact(
                contactIds[1],
                bxC2Name.getText().toString(),
                bxC2LastName.getText().toString(),
                bxC2Number.getText().toString(),
                bxC2Relation.getText().toString()
        );

        if (resUser > 0 && resAddress > 0 && resC1 > 0 && resC2 > 0) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            Toast.makeText(this, "Error al actualizar algunos datos", Toast.LENGTH_SHORT).show();
        }
    }
}