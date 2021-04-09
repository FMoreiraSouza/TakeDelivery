package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.adapter.AdapterListViewPersonalizada;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardapioActivity extends AppCompatActivity {

    public static ArrayList<Produto> cardapio;
    int selected;
    AdapterListViewPersonalizada adapter;

    ListView listViewProdutos;
    String nomeEmpresa;
    TextView textViewNomeEmpresa;
    Boolean isEmpresa;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            textViewNomeEmpresa = findViewById(R.id.textViewNomeRest);

            nomeEmpresa = (String) getIntent().getExtras().get("nomeEmpresa");
            textViewNomeEmpresa.setText(nomeEmpresa);
        }

        cardapio = CardapioActivity.cardapio;


        selected = -1;

        adapter = new AdapterListViewPersonalizada(cardapio, this);

        listViewProdutos = (ListView) findViewById(R.id.listViewProdutosEmp);

        listViewProdutos.setAdapter(adapter);

    }
    public void addCarrinho (View view){
        Intent intent = new Intent(this, AdicionaProdutoCarrinhoActivity.class);
        Produto produto = cardapio.get(selected);

        intent.putExtra( "nomeEmpresa", nomeEmpresa );

        intent.putExtra( "id", produto.getId() );
        intent.putExtra( "nome", produto.getNome() );
        intent.putExtra( "descricao", produto.getDescricao() );
        intent.putExtra( "preco", produto.getPreco() );
        startActivity(intent);

    }





}