package com.example.DataBase;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    // Nuevo método para crear un intent de cámara con URI
    public static Intent obtenerIntentCamara(Context context, File archivoFoto, Uri[] outUri) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", archivoFoto);
        outUri[0] = uri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    public static Intent obtenerIntentGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        return intent;
    }

    public static String procesarResultadoImagen(Context context, int requestCode, Uri uriCamara, @Nullable Intent data) {
        Uri uri = (requestCode == 1) ? uriCamara : data.getData();
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            File archivo = new File(context.getFilesDir(), "foto_perfil.jpg");
            OutputStream outputStream = new FileOutputStream(archivo);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            inputStream.close();
            outputStream.close();

            return archivo.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap obtenerBitmapDesdeResultado(Context context, int requestCode, Uri uriCamara, @Nullable Intent data) {
        try {
            Uri uri = (requestCode == 1) ? uriCamara : data.getData();
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
