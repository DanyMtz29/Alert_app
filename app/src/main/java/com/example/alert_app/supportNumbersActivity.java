package com.example.alert_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class supportNumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_support_numbers);
    }

    public void backSupport(View v){
        Intent i = new Intent(supportNumbersActivity.this, informationActivity.class);
        startActivity(i);
        this.finish();
    }
    public void CJEM(View v) {
        Uri number = Uri.parse("tel:8712226300");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
    }

    public void IMM(View v) {
        Uri number = Uri.parse("tel:8715007419");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
    }

    public void support911(View v) {
        Uri number = Uri.parse("tel:911");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);

    }

    public void LNCV(View v) {
        Uri number = Uri.parse("tel:8009112511");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);

    }

    public void policia(View v) {
        Uri number = Uri.parse("tel:8717290099");
        Intent intent = new Intent(Intent.ACTION_DIAL, number);

    }

}