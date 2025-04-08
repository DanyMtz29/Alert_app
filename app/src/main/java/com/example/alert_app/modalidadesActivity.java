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

        //Recuadoro de modalidad familiar
        applyListener(findViewById(R.id.header1), findViewById(R.id.desc1), findViewById(R.id.flecha));

        //Recuadro de modalidad laboral
        applyListener(findViewById(R.id.layout2),findViewById(R.id.text_desc_v_laboral),findViewById(R.id.flecha2));

        //Recuadro de modalidad escolar
        applyListener(findViewById(R.id.layoutEscolar), findViewById(R.id.text_desc_v_escolar),findViewById(R.id.flechaEscolar) );

        //Recuadro de modalidad comunidad
        applyListener(findViewById(R.id.layoutComunidad), findViewById(R.id.text_desc_v_comunidad), findViewById(R.id.flechaComunidad));

        //Recuadro de modalidad institucional
        applyListener(findViewById(R.id.layoutInstitucional), findViewById(R.id.text_desc_v_institucional), findViewById(R.id.flechaInstitucional));

        //Recuadro de modalidad Noviazgo
        applyListener(findViewById(R.id.layoutNoviazgo), findViewById(R.id.text_desc_v_noviazgo), findViewById(R.id.flechaNoviazgo));

    }

    public void applyListener(LinearLayout l, TextView t, ImageView im){
        l.setOnClickListener( v -> {
            if (t.getVisibility() == View.GONE) {
                t.setVisibility(View.VISIBLE);
                im.setImageResource(R.drawable.flecha_arriba);
            } else {
                t.setVisibility(View.GONE);
                im.setImageResource(R.drawable.flecha_abajo);
            }
        });
    }
}