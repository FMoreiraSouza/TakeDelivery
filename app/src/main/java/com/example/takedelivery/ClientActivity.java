package com.example.takedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.takedelivery.adapter.AdapterListViewEmpresas;
import com.example.takedelivery.adapter.AdapterListViewPersonalizada;
import com.example.takedelivery.model.Categoria;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {
    Empresa empresa = resa("28.046.882/0001-45", "Delivrey Menu", "8599568791", "60125151", "CE", "Fortaleza", "Dionisio Torres", "Osvaldo Cruz", "2085", Categoria.BRASILEIRA);
    ArrayList<Produto> cardapio = new ArrayList<Produto>();

    ArrayList<Empresa> empresas = new ArrayList<Empresa>();
    int selected;
    AdapterListViewEmpresas adapter;

    ListView listViewEmpresas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar( toolbar );


        //cardapio.add(new Produto("Vatapá de frango","Acompanha arroz branco e farofinha de mandioca. Vatapá de frando desfiado e finalizado com batata palha.", (float) 19.90));
        //cardapio.add(new Produto("Frango com salsa de ervas frescas","Acompanha arroz branco e purê de batatas. Filé de peito de frango em tiras adicnado de molho a base de azeite com ervas frescas.", (float) 19.90));

        empresa.setCardapio(cardapio);
        empresas.add(empresa);

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
        CardapioActivity.cardapio = empresa.getCardapio();
        startActivity(intent);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_client, menu);

        return super.onCreateOptionsMenu(menu);
    }

    
}