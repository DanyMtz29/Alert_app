package com.example.alert_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityIntermedio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (yaRegistrado()) {
            startActivity(new Intent(this, Principal.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

        finish();
    }

    private boolean yaRegistrado() {
        SharedPreferences prefs = getSharedPreferences("registro", MODE_PRIVATE);
        return prefs.getBoolean("yaRegistrado", false);
    }
}