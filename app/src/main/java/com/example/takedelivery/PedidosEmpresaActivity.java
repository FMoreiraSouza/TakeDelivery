package com.example.takedelivery;

import android.os.Bundle;

import com.example.takedelivery.adapter.AdapterListViewPedidos;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Pedido;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PedidosEmpresaActivity extends AppCompatActivity {
    public static ArrayList<Pedido> pedidos;
    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Produto> produtos = new ArrayList<>();
    public static DatabaseReference empresaLogadaRef;
    int selected;
    AdapterListViewPedidos adapter;
    Cliente cliente;
    ListView listViewPedidos;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference();
    private ValueEventListener valueEventListenerPedidos;
    private DatabaseReference pedidosRef;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_empresa);


        if( getIntent().getExtras() != null ){
            status = (String) getIntent().getExtras().get( "status" );

        }

        empresaLogadaRef = PedidosEmpresaActivity.empresaLogadaRef;
        pedidosRef = empresaLogadaRef.child("pedidos");

        pedidos = new ArrayList<Pedido>();

        selected = -1;

        adapter = new AdapterListViewPedidos(pedidos, clientes,  produtos, this);

        listViewPedidos = (ListView) findViewById(R.id.listViewPedidos);

        listViewPedidos.setAdapter(adapter);
        listViewPedidos.setSelector(R.color.corSelect);


        listViewPedidos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                selected = position;
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
        buscarPedidos();
    }

    @Override
    public void onStop() {
        super.onStop();
        pedidosRef.removeEventListener( valueEventListenerPedidos );
    }

    public void buscarPedidos(){

        valueEventListenerPedidos = pedidosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidos.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Pedido pedido = ds.getValue(Pedido.class);
                    if(pedido.getStatus().equals(status)){
                        pedidos.add(pedido);
                    }
                }
                buscarClientes();
//                buscarProdutos();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void buscarClientes(){
        mDatabaseReference.child("clientes").addValueEventListener(new ValueEventListener() {
            @Override//                activity.updateCardapio( cardapio );

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientes.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    clientes.add(ds.getValue(Cliente.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void buscarProdutos(){

        empresaLogadaRef.child("produtos").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    produtos.add(ds.getValue(Produto.class));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void mudaStatus(View view){
        adapter.botaoMudaStatus();
        buscarPedidos();
    }


}