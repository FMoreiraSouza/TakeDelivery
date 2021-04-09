package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class AdicionaProdutoCarrinhoActivity extends AppCompatActivity {
    TextView editTextNome;
    TextView editTextDescricao;
    TextView editTextPreco;
    EditText editTextQtd;

    String nome ;
    String descricao ;
    Float preco ;
    String nomeEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_produto_carrinho);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextNome = findViewById( R.id.textViewNomePro );
        editTextDescricao = findViewById( R.id.textViewDescricaoPro );
        editTextPreco = findViewById( R.id.textViewPrecoPro );


        if( getIntent().getExtras() != null ){
            nome = (String) getIntent().getExtras().get( "nome" );
             descricao = (String) getIntent().getExtras().get( "descricao" );
             preco = (Float) getIntent().getExtras().get( "preco" );
            nomeEmpresa = (String) getIntent().getExtras().get( "nomeEmpresa" );

            editTextNome.setText(nome);
            editTextDescricao.setText(descricao);
            Locale ptBr = new Locale("pt", "BR");
            editTextPreco.setText( NumberFormat.getCurrencyInstance(ptBr).format(preco));
        }
    }

    public void addProCarrinho( View view ){
        Intent intent = new Intent(this, CarrinhoActivity.class);
        editTextQtd = findViewById( R.id.editTexQtd );
        String qtd = editTextQtd.getText().toString();
        Float quantidade = new Float(qtd);
        Float valorTotal = preco * quantidade;
        intent.putExtra("nomeEmpresa", nomeEmpresa );
        intent.putExtra("nome", nome );

        intent.putExtra("preco", preco );
        intent.putExtra("precoTotal",valorTotal);
        startActivity(intent);

    }
}