package com.example.takedelivery.Empresa;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.Empresa.CardapioEmpresaActivity;
import com.example.takedelivery.PedidosEmpresaActivity;
import com.example.takedelivery.R;
import com.example.takedelivery.firebase.EmpresaFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Produto;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import com.google.firebase.database.ChildEventListener;
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



        String identificadorUsuario = EmpresaFirebase.getIdentificarEmpresa();
        database = FirebaseItems.getFirebaseDatabase();
        empresaLogadaRef = database.child("empresas")
                .child( identificadorUsuario );


//        return super.onCreateOptionsMenu(menu);
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