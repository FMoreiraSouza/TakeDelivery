package com.example.takedelivery.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.takedelivery.empresa.AdicionarProduto;
import com.example.takedelivery.R;
import com.example.takedelivery.adapter.AdapterListViewEmpresas;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ArrayList<Produto> cardapio = new ArrayList<Produto>();

    ArrayList<Empresa> empresas = new ArrayList<Empresa>();
    int selected;
    AdapterListViewEmpresas adapter;

    ListView listViewEmpresas;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Take Delivery Cliente");
        setSupportActionBar(toolbar);


    }

    /*selected = -1;
    adapter = new AdapterListViewEmpresas(empresas, this);

    listViewEmpresas = (ListView) findViewById(R.id.listViewRestaurantes);

        listViewEmpresas.setadapter(adapter);
//        listViewEmpresas.setSelector(R.color.corSelect);


        listViewEmpresas.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1,
        int position, long arg3) {

            selected = position;
            verCardapio(arg1);
        }

    });*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cliente, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.set :
                abrirConfiguracoes();
                break;
            case R.id.addshopcar :
                abrirNovoProduto();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void deslogarUsuario(){
        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void verCardapio (View view){
        Intent intent = new Intent(this, CardapioActivity.class);
//        CardapioActivity.cardapio = empresa.getCardapio();
//        intent.putExtra("nomeEmpresa", empresa.getNomeFantasia() );

        startActivity(intent);

    }


    private void abrirConfiguracoes(){
        startActivity(new Intent(ClienteActivity.this, ConfigurarCliente.class));
    }

    private void abrirNovoProduto(){
        startActivity(new Intent(ClienteActivity.this, AdicionarProduto.class));
    }



}