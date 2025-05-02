package com.example.DataBase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class getImageUbi {

    public static double[] obtenerUbicacion(Activity activity, FusedLocationProviderClient client) {
        double[] ubicacion = {-1.0, -1.0};

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            client.getLastLocation()
                    .addOnSuccessListener(activity, location -> {
                        if (location != null) {
                            ubicacion[0] = location.getLatitude();
                            ubicacion[1] = location.getLongitude();
                        }
                    });
        }

        return ubicacion;
    }

    public static Intent obtenerIntentCamara() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public static Intent obtenerIntentGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }

    public static String procesarResultadoImagen(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bitmap imagen = null;
            try {
                if (requestCode == 1) { // Cámara
                    imagen = (Bitmap) data.getExtras().get("data");
                } else if (requestCode == 2) { // Galería
                    Uri imageUri = data.getData();
                    imagen = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
                }

                if (imagen != null) {
                    return guardarImagenLocal(activity, imagen);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String guardarImagenLocal(Context context, Bitmap bitmap) throws IOException {
        File directorio = new File(context.getFilesDir(), "imagenes");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        String nombreArchivo = "img_" + System.currentTimeMillis() + ".jpg";
        File archivo = new File(directorio, nombreArchivo);

        FileOutputStream fos = new FileOutputStream(archivo);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();

        return archivo.getAbsolutePath();
    }
}