package com.example.takedelivery.cliente;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takedelivery.adapter.AdapterListViewCardapioCliente;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardapioActivity extends AppCompatActivity {

    public static Empresa empresa;
    public static Cliente cliente;
    public ArrayList<Produto> cardapio = new ArrayList<>();
    int selected;
    AdapterListViewCardapioCliente adapter;

    ListView listViewProdutos;
    String nomeEmpresa;
    TextView textViewNomeEmpresa;
    Boolean isEmpresa;

    private ValueEventListener valueEventListenerProdutos;
    private DatabaseReference database;
    private DatabaseReference produtosRef;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);
        empresa = CardapioActivity.empresa;
        cliente = CardapioActivity.cliente;

        produtosRef = mDatabaseReference.child("empresas").child(empresa.getId()).child("produtos");
//        if (getIntent().getExtras() != null) {
            textViewNomeEmpresa = findViewById(R.id.textViewNomeRest);
            textViewNomeEmpresa.setText(empresa.getNomeFantasia());
   //     }

//        cardapio = CardapioActivity.empresa.getProdutos();

        selected = -1;

        adapter = new AdapterListViewCardapioCliente(cardapio, this);

        listViewProdutos = (ListView) findViewById(R.id.listViewProdutos);

        listViewProdutos.setAdapter(adapter);
        listViewProdutos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                selected = position;
                addCarrinho (arg1);
            }

        });

    }

    public void addCarrinho (View view){
        Intent intent = new Intent(this, AdicionaProdutoCarrinhoActivity.class);
        Produto produto = cardapio.get(selected);
        AdicionaProdutoCarrinhoActivity.empresa = empresa;
        AdicionaProdutoCarrinhoActivity.produto = produto;
        AdicionaProdutoCarrinhoActivity.cliente = cliente;

//        intent.putExtra( "nomeEmpresa", nomeEmpresa );

        intent.putExtra( "id", produto.getId() );
        intent.putExtra( "nome", produto.getNome() );
        intent.putExtra( "descricao", produto.getDescricao() );
        intent.putExtra( "preco", produto.getPreco() );
        startActivity(intent);

    }
    @Override
    public void onStart() {
        super.onStart();
        buscarProdutos();
    }

    @Override
    public void onStop() {
        super.onStop();
        produtosRef.removeEventListener( valueEventListenerProdutos );
    }

    public void buscarProdutos(){

        valueEventListenerProdutos = produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cardapio.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    cardapio.add( ds.getValue(Produto.class));
                }
//                if(!cardapio.isEmpty()){
//                    TextView textView = (TextView) findViewById(R.id.textView15);
//                    ((ViewGroup)textView.getParent()).removeView(textView);
//                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}