package com.example.alert_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class informationActivity extends AppCompatActivity {

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion);
    }

    public void typesViolence(View v){
        i = new Intent(informationActivity.this, TiposViolencia.class);
        startActivity(i);
        this.finish();
    }
    public void alertButton(View v){
        i = new Intent(informationActivity.this, Principal.class);
        startActivity(i);
        this.finish();
    }

    public void modalidadesButton(View v){
        i = new Intent(informationActivity.this, modalidadesActivity.class);
        startActivity(i);
        this.finish();
    }

    public void centerInformationButton(View v){
        i = new Intent(informationActivity.this, centerInformationACtivity.class);
        startActivity(i);
        this.finish();
    }

    public void supportNumbersButton(View v){
        i = new Intent(informationActivity.this, supportNumbersActivity.class);
        startActivity(i);
        this.finish();
    }

    public void violentometro(View v){
        i = new Intent(informationActivity.this, violentometroActivity.class);
        startActivity(i);
        this.finish();
    }

    public void backCenter(View v){
        Intent i = new Intent(this, informationActivity.class);
        startActivity(i);
        this.finish();
    }

    public void backInformation(View v){
        this.finish();
    }
}