package com.example.takedelivery.cliente;

import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.adapter.AdapterListViewPedidos;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.EmpresaFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Pedido;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PedidosCliActivity extends AppCompatActivity {
    public static ArrayList<Pedido> pedidos = new ArrayList<>();
    private DatabaseReference database;
    public DatabaseReference clienteLogadoRef;
    private DatabaseReference pedidosRef;
    private ValueEventListener childEventListenerPedidos;
    int selected;
    AdapterListViewPedidos adapter;
    ListView listViewPedidos;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_pedidos_cli);
        String identificadorUsuario = ClienteFirebase.getIdentificarCliente();
        database = FirebaseItems.getFirebaseDatabase();
        clienteLogadoRef = database.child("Clientes")
                .child( identificadorUsuario );
        pedidosRef = clienteLogadoRef.child("pedidos");

        if( getIntent().getExtras() != null ){
            status = (String) getIntent().getExtras().get( "status" );

        }

        selected = -1;

        adapter = new AdapterListViewPedidos(pedidos, false,this);

        listViewPedidos = (ListView) findViewById(R.id.listViewPedidosCli);

        listViewPedidos.setAdapter(adapter);
        listViewPedidos.setSelector(R.color.corSelect);


    }
    @Override
    public void onStart() {
        super.onStart();
        buscarPedidos();


    }

    @Override
    public void onStop() {
        super.onStop();
        pedidosRef.removeEventListener( childEventListenerPedidos);


    }

    public void buscarPedidos(){

        childEventListenerPedidos = pedidosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidos.clear();
                String dataHoje = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("status").getValue().toString().equals(status)
                            || status.equals("todos")||
                            (status.equals("Preparando Pedido") && ds.child("status").getValue().toString().equals("Saiu para entrega") )
                    || (status.equals("Preparando Pedido") && ds.child("status").getValue().toString().equals("Pendente aprovação") )) {
                        pedidos.add(ds.getValue(Pedido.class));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}