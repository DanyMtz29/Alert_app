package com.example.alert_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class modalidadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modalidades);

        LinearLayout header1 = findViewById(R.id.header1);
        TextView desc1 = findViewById(R.id.desc1);
        ImageView flecha1 = findViewById(R.id.flecha1);

        header1.setOnClickListener(v -> {
            if (desc1.getVisibility() == View.GONE) {
                desc1.setVisibility(View.VISIBLE);
                flecha1.setImageResource(R.drawable.flecha_arriba);
            } else {
                desc1.setVisibility(View.GONE);
                flecha1.setImageResource(R.drawable.flecha_abajo);
            }
        });
    }
}