package com.example.takedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirBegin();
            }
        }, 5000);

    }

    private void abrirBegin() {
        Intent i  = new Intent(MainActivity.this, Escolha.class);
        startActivity(i);
        finish();
    }
}