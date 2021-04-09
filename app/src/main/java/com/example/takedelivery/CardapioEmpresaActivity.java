package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.takedelivery.adapter.AdapterListViewPersonalizada;

import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CardapioEmpresaActivity extends AppCompatActivity {

    public static ArrayList<Produto> cardapio = new ArrayList<>();
    int selected;
    AdapterListViewPersonalizada adapter;

    ListView listViewProdutos;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    private ValueEventListener valueEventListenerProdutos;
    public static DatabaseReference empresaLogadaRef;
    private DatabaseReference produtosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_empresa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        empresaLogadaRef = CardapioEmpresaActivity.empresaLogadaRef;
        produtosRef = empresaLogadaRef.child("produtos");
        selected = -1;

        adapter = new AdapterListViewPersonalizada(cardapio, this);

        listViewProdutos = (ListView) findViewById(R.id.listViewProdutosEmp);

        listViewProdutos.setAdapter(adapter);
        listViewProdutos.setSelector(R.color.corSelect);


        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                selected = position;
            }

        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cardapio, menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch(item.getItemId())
        {
            case R.id.adicionar:
                adicionarProduto();
                break;
            case R.id.editar:
                editarProduto();
                break;
            case R.id.excluir:
                excluirProduto();
                break;

        }
        return true;
    }

    public void adicionarProduto(){
        Intent intent = new Intent( this, CadastroProdutoActivity.class );
        CadastroProdutoActivity.empresaLogadaRef = empresaLogadaRef;
        startActivity(intent);
    }
    public void editarProduto(){
        Intent intent = new Intent( this, CadastroProdutoActivity.class );
        Produto produto = cardapio.get(selected);
        CadastroProdutoActivity.empresaLogadaRef = empresaLogadaRef;
        CadastroProdutoActivity.produto = produto;
        intent.putExtra( "idEdit", produto.getId() );
        intent.putExtra( "nome", produto.getNome() );
        intent.putExtra( "descricao", produto.getDescricao() );
        intent.putExtra( "preco", produto.getPreco() );

        startActivity( intent );
    }
    public void excluirProduto(){
        Produto produto = cardapio.get(selected);
        produto.excluir(empresaLogadaRef);
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

