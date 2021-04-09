package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.adapter.AdapterListViewEmpresas;
import com.example.takedelivery.model.Categoria;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.math.BigDecimal;
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
        setSupportActionBar( toolbar );
//        Produto produto =new Produto("Frango com salsa de ervas frescas","Acompanha arroz branco e purê de batatas. Filé de peito de frango em tiras adicnado de molho a base de azeite com ervas frescas.", (float) 19.90);
//        mDatabaseReference = mDatabase.getReference (). child ("produtos").push();
//        mDatabaseReference.setValue (produto);

       // cardapio.add(new Produto("Vatapá de frango","Acompanha arroz branco e farofinha de mandioca. Vatapá de frando desfiado e finalizado com batata palha.", (float) 19.90));
        //cardapio.add(new Produto("Frango com salsa de ervas frescas","Acompanha arroz branco e purê de batatas. Filé de peito de frango em tiras adicnado de molho a base de azeite com ervas frescas.", (float) 19.90));

//        empresa.setCardapio(cardapio);
//        empresas.add(empresa);
//        empresas.add(new Empresa("28.046.882/0001-45", "Delivery Menu", "8599568791", "60125151", "CE", "Fortaleza", "Dionisio Torres", "Osvaldo Cruz", "2085", Categoria.BRASILEIRA));

        selected = -1;
        adapter = new AdapterListViewEmpresas(empresas, this);

        listViewEmpresas = (ListView) findViewById(R.id.listViewRestaurantes);

        listViewEmpresas.setAdapter(adapter);
//        listViewEmpresas.setSelector(R.color.corSelect);


        listViewEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                selected = position;
                verCardapio(arg1);
            }

        });



    }
    public void verCardapio (View view){
        Intent intent = new Intent(this, CardapioActivity.class);
//        CardapioActivity.cardapio = empresa.getCardapio();
//        intent.putExtra("nomeEmpresa", empresa.getNomeFantasia() );

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_client, menu);

        return super.onCreateOptionsMenu(menu);
    }
}