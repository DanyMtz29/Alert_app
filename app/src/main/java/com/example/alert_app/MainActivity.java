package com.example.alert_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    //Controler
    Controller controller;

    EditText bxUName, bxULastName, bxUAdrCity, bxUAdrColony, bxUAdrStreet, bxUAdrNumber, bxUAdrCp, bxUNumberTel;

    EditText bxC1Name, bxC1LastName, bxC1Number, bxC1Relation,bxC2Name, bxC2LastName, bxC2Number, bxC2Relation;

    ImageButton imgProfile;

    Button btnRegister;

    String imgPath;

    FusedLocationProviderClient fusedLocationClient;
    ActivityResultLauncher<Intent> imageResultLauncher;

    CheckBox ckBox;

    int currentRequestCode = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        controller = new Controller(this);

        bxUName = (EditText) findViewById(R.id.boxUserName);
        bxULastName = (EditText) findViewById(R.id.boxUserLastName);
        bxUAdrCity = (EditText) findViewById(R.id.boxAddressCity);
        bxUAdrColony = (EditText) findViewById(R.id.boxAddressColony);
        bxUAdrStreet= (EditText) findViewById(R.id.boxAddressStreet);
        bxUAdrNumber = (EditText) findViewById(R.id.boxAddressNumber);
        bxUAdrCp = (EditText) findViewById(R.id.boxAddressCP);
        bxUNumberTel = (EditText) findViewById(R.id.boxNumberTel);

        bxC1Name = (EditText) findViewById(R.id.boxContact1Name);
        bxC1LastName = (EditText) findViewById(R.id.boxContact1LastName);
        bxC1Number = (EditText) findViewById(R.id.boxContact1Number);
        bxC1Relation = (EditText) findViewById(R.id.boxContact1Relation);
        bxC2Name = (EditText) findViewById(R.id.boxContact2Name);
        bxC2LastName = (EditText) findViewById(R.id.boxContact2LastName);
        bxC2Number = (EditText) findViewById(R.id.boxContact2Number);
        bxC2Relation = (EditText) findViewById(R.id.boxContact2Relation);
        ckBox = (CheckBox) findViewById(R.id.checkBox);

        imgPath = "";

        imgProfile = (ImageButton) findViewById(R.id.imgProfileButton);
        btnRegister = (Button) findViewById(R.id.buttonRegister);

        imgProfile.setOnClickListener(v -> {
            String[] opciones = {"Tomar Foto", "Elegir de Galería"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona una opción")
                    .setItems(opciones, (dialog, which) -> {
                        Intent intent = (which == 0)
                                ? getImageUbi.obtenerIntentCamara()
                                : getImageUbi.obtenerIntentGaleria();
                        currentRequestCode = (which == 0) ? REQUEST_CAMERA : REQUEST_GALLERY;
                        imageResultLauncher.launch(intent);
                    }).show();
        });
        imageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imgPath = getImageUbi.procesarResultadoImagen(this, currentRequestCode, result.getResultCode(), result.getData());
                        if (!imgPath.isEmpty()) {
                            imgProfile.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                            Toast.makeText(this, "Imagen guardada: " + imgPath, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void register(View v){
        if( check() ){
            Toast.makeText(this, "Son obligatorios todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if( !ckBox.isChecked() ){
            Toast.makeText(this, "Se deben de aceptar los terminos y condiciones para usar la app", Toast.LENGTH_SHORT).show();
            return;
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
                    //Para intercalar entre pantalla principal y registro
                    SharedPreferences prefs = getSharedPreferences("registro", MODE_PRIVATE);
                    prefs.edit().putBoolean("yaRegistrado", true).apply();
                }else
                    Toast.makeText(this, "Error al guardar la alerta", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean check(){
        return bxUName.getText().toString().isEmpty() ||
                bxULastName.getText().toString().isEmpty()||
                bxUAdrCity.getText().toString().isEmpty()||
                bxUAdrStreet.getText().toString().isEmpty()||
                bxUAdrNumber.getText().toString().isEmpty()||
                bxUAdrCp.getText().toString().isEmpty()||
                bxUNumberTel.getText().toString().isEmpty()||
                bxC1Name.getText().toString().isEmpty()||
                bxC1LastName.getText().toString().isEmpty()||
                bxC1Number.getText().toString().isEmpty()||
                bxC1Relation.getText().toString().isEmpty()||
                bxC2Name.getText().toString().isEmpty()||
                bxC2LastName.getText().toString().isEmpty()||
                bxC2Number.getText().toString().isEmpty()||
                bxC2Relation.getText().toString().isEmpty();
    }

    public void leerDatos(View v) {
        String[] userData = controller.readUser();

        if (userData[0] == null) {
            Toast.makeText(this, "No hay usuario registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        bxUName.setText(userData[1]);
        bxULastName.setText(userData[2]);
        bxUNumberTel.setText(userData[3]);
        bxUAdrCity.setText(userData[4]);
        bxUAdrColony.setText(userData[5]);
        bxUAdrStreet.setText(userData[6]);
        bxUAdrNumber.setText(userData[7]);
        bxUAdrCp.setText(userData[8]);

        //IMG PROFILE
        String rutaImagen = userData[9];
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            imgProfile.setImageBitmap(BitmapFactory.decodeFile(rutaImagen));
        }

        // Contacts
        long id_user = Long.parseLong(userData[0]);
        String[] contactos = controller.readContact(id_user);

        if (contactos[0] != null) {
            bxC1Name.setText(contactos[0]);
            bxC1LastName.setText(contactos[1]);
            bxC1Number.setText(contactos[2]);
            bxC1Relation.setText(contactos[3]);
            //Contact 2
            if (contactos[4] != null) {
                bxC2Name.setText(contactos[4]);
                bxC2LastName.setText(contactos[5]);
                bxC2Number.setText(contactos[6]);
                bxC2Relation.setText(contactos[7]);
            }
        }else{
            Toast.makeText(this, "No existe el contacto 1", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
        controller.resetDatabase(this);//===========================borrar
    }
}