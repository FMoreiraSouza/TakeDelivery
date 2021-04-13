package com.example.takedelivery.empresa;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.firebase.EmpresaFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Pedido;
import com.example.takedelivery.model.Produto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.firebase.CryptografiaBase64;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EmpresaActivity extends AppCompatActivity {

    private DatabaseReference database;
    private DatabaseReference empresaLogadaRef;
    private ValueEventListener childEventListenerEmpresa;
    private DatabaseReference pedidosRef;
    private ValueEventListener childEventListenerPedidos;

    TextView textViewValorHj;
    TextView textViewQtdPedidosHj;
    TextView textViewNomeEmpresa;
    String nomeEmpresa;
    Empresa empresa = new Empresa();
    ArrayList<Pedido> pedidos = new ArrayList<>();
    private Button configurarempresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        String identificadorUsuario = EmpresaFirebase.getIdentificarEmpresa();
        database = FirebaseItems.getFirebaseDatabase();
        empresaLogadaRef = database.child("Empresas")
                .child( identificadorUsuario );
        pedidosRef = empresaLogadaRef.child("pedidos");


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
        intent.putExtra("status", "Pendente aprovação");
        startActivity(intent);
    }

    public void verPedidosFinalizados(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);
        intent.putExtra("status", "Finalizado");
        startActivity(intent);
    }
    public void verPedidosAndamento(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);
        intent.putExtra("status", "Preparando Pedido");
        startActivity(intent);
    }

    public void verHistoricoPedidos(View view){
        Intent intent = new Intent(this, PedidosEmpresaActivity.class);
        intent.putExtra("status", "todos");
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        buscarPedidos();
        buscarEmpresa();

        textViewQtdPedidosHj = (TextView) findViewById(R.id.textViewQtdPedidosHj);
        textViewValorHj = (TextView) findViewById(R.id.textViewValorPedidosHj);
        textViewNomeEmpresa = (TextView) findViewById(R.id.textViewNome);

        int qtdPedidos = pedidos.size();
        textViewQtdPedidosHj.setText(String.valueOf(qtdPedidos));
        float valorHj = 0;
        for(Pedido ped: pedidos){
            valorHj += ped.getValorTotal();
        }
        textViewValorHj.setText(String.valueOf(valorHj));
        textViewNomeEmpresa.setText(nomeEmpresa);
    }

    @Override
    public void onStop() {
        super.onStop();
        empresaLogadaRef.removeEventListener( childEventListenerEmpresa);
        pedidosRef.removeEventListener( childEventListenerPedidos);


    }

    public void buscarEmpresa() {

        childEventListenerEmpresa = empresaLogadaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nomeEmpresa = dataSnapshot.child("nomeFantasia").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void buscarPedidos(){

        childEventListenerPedidos = pedidosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidos.clear();
                String dataHoje = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(dataHoje.equals(ds.child("data").getValue().toString())) {
                        pedidos.add(ds.getValue(Pedido.class));
                    }
                }

//                if(!cardapio.isEmpty()){
//                    TextView textView = (TextView) findViewById(R.id.textView15);
//                    ((ViewGroup)textView.getParent()).removeView(textView);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}