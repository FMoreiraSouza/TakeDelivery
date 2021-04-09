package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.model.Categoria;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class EmpresaActivity extends AppCompatActivity {
//    Empresa empresa = new Empresa(0,"28.046.882/0001-45", "Delivrey Menu", "8599568791", "60125151", "CE", "Fortaleza", "Dionisio Torres", "Osvaldo Cruz", "2085", Categoria.BRASILEIRA);
    ArrayList<Produto> cardapio = new ArrayList<Produto>();
    private DatabaseReference database;
    private DatabaseReference empresaLogadaRef;
    private ChildEventListener childEventListenerProdutos;
    private ChildEventListener childEventListenerPedidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String identificadorUsuario = EmpresaFirebase.getIdentificarEmpresa();
        database = FirebaseItems.getFirebaseDatabase();
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
                break;
            case R.id.sair:
                break;


        }
        return true;
    }


    public void verCardapio (View view){
        Intent intent = new Intent(this, CardapioEmpresaActivity.class);
        CardapioEmpresaActivity.empresaLogadaRef = empresaLogadaRef;
        startActivity(intent);

        }

        public void verPedidosPendentes(View view){
            Intent intent = new Intent(this, PedidosEmpresaActivity.class);
            PedidosEmpresaActivity.empresaLogadaRef = empresaLogadaRef;
            intent.putExtra("status", "Pendente aprovação");
            startActivity(intent);
        }

    public void verPedidosFinalizados(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);
        intent.putExtra("status", "Finalizado");

//            intent.putParcelableArrayListExtra( "cardapio", (ArrayList<? extends Parcelable>) cardapio);
        startActivity(intent);
    }
    public void verPedidosAndamento(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);
        intent.putExtra("status", "Preparando Pedido");

//            intent.putParcelableArrayListExtra( "cardapio", (ArrayList<? extends Parcelable>) cardapio);
        startActivity(intent);
    }
}