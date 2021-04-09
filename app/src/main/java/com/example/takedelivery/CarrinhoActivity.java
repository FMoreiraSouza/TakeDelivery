package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CarrinhoActivity extends AppCompatActivity {
    TextView editTextNome;
    TextView editTextPreco;
    TextView editTextTotal;
    TextView textViewNomeEmpresa;

    String nome ;
    Float preco ;
    Float total ;
    String nomeEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNome = findViewById( R.id.textViewNomeProduto );
        editTextPreco = findViewById( R.id.textViewPrecoProduto );
        editTextTotal = findViewById( R.id.textViewTotal );
        textViewNomeEmpresa = findViewById( R.id.textViewNomeEmpresa );

        if( getIntent().getExtras() != null ){
            nome = (String) getIntent().getExtras().get( "nome" );
            preco = (Float) getIntent().getExtras().get( "preco" );
            total = (Float) getIntent().getExtras().get( "precoTotal" );
            nomeEmpresa = (String) getIntent().getExtras().get( "nomeEmpresa" );

            textViewNomeEmpresa.setText(nomeEmpresa);
            editTextNome.setText(nome);
            Locale ptBr = new Locale("pt", "BR");
            editTextPreco.setText( NumberFormat.getCurrencyInstance(ptBr).format(preco));
            editTextTotal.setText( NumberFormat.getCurrencyInstance(ptBr).format(total));



        }
    }
    public void limparCarrinho(View view) {
        editTextNome.setText("");
        editTextPreco.setText("");
        editTextTotal.setText("");
        Intent intent = new Intent(this, CardapioActivity.class);
        startActivity(intent);

    }
    public void fazerPedido(View view) {
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);

    }
}