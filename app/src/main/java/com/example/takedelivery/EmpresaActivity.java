package com.example.takedelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.takedelivery.R;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EmpresaActivity extends AppCompatActivity {

    //    Empresa empresa = new Empresa(0,"28.046.882/0001-45", "Delivrey Menu", "8599568791", "60125151", "CE", "Fortaleza", "Dionisio Torres", "Osvaldo Cruz", "2085", Categoria.BRASILEIRA);
    ArrayList<Produto> cardapio = new ArrayList<Produto>();
    private DatabaseReference database;
    private DatabaseReference empresaLogadaRef;
    private ChildEventListener childEventListenerProdutos;
    private ChildEventListener childEventListenerPedidos;
    private Button configurarempresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String identificadorUsuario = EmpresaFirebase.getIdentificarEmpresa();
        database = FirebaseOptions.getFirebase();
        empresaLogadaRef = database.child("empresas")
                .child( identificadorUsuario );


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_empresa, menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch(item.getItemId())
        {
            case R.id.editar:
                DefinirEmpresa(configurarempresa);
                break;
            case R.id.sair:
                break;


        }
        return true;
    }

    public void DefinirEmpresa(View view){
        Intent i  = new Intent(EmpresaActivity.this, ConfigurarEmpresa.class);
        startActivity(i);
        finish();
    }


    public void verCardapio (View view){
        Intent intent = new Intent(this, CardapioEmpresaActivity.class);
        startActivity(intent);

    }

    public void verPedidosPendentes(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);
        startActivity(intent);
    }

    public void verPedidosFinalizados(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);

//            intent.putParcelableArrayListExtra( "cardapio", (ArrayList<? extends Parcelable>) cardapio);
        startActivity(intent);
    }
    public void verPedidosAndamento(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);

//            intent.putParcelableArrayListExtra( "cardapio", (ArrayList<? extends Parcelable>) cardapio);
        startActivity(intent);
    }
}