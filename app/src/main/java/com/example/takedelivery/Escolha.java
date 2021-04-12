package com.example.takedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.takedelivery.acesso.AcessoCliente;
import com.example.takedelivery.acesso.AcessoEmpresa;

public class Escolha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

    }

    public void abrirAcessoCliente(View view){
        Intent i  = new Intent(Escolha.this, AcessoCliente.class);
        startActivity(i);
        finish();
    }
    public void abrirAcessoEmpresa(View view){
        Intent j  = new Intent(Escolha.this, AcessoEmpresa.class);
        startActivity(j);
        finish();
    }


}