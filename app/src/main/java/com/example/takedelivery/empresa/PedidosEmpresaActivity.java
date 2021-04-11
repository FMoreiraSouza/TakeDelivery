package com.example.takedelivery.empresa;

import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.adapter.AdapterListViewPedidos;
import com.example.takedelivery.firebase.EmpresaFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Empresa;
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
    public static ArrayList<Pedido> pedidos = new ArrayList<>();
    int selected;
    AdapterListViewPedidos adapter;
    ListView listViewPedidos;
    private DatabaseReference database;
    public DatabaseReference empresaLogadaRef;
    private DatabaseReference pedidosRef;
    private ValueEventListener childEventListenerPedidos;



    String status;
    Empresa empresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_empresa);


        if( getIntent().getExtras() != null ){
            status = (String) getIntent().getExtras().get( "status" );

        }

        String identificadorUsuario = EmpresaFirebase.getIdentificarEmpresa();
        database = FirebaseItems.getFirebaseDatabase();
        empresaLogadaRef = database.child("empresas")
                .child( identificadorUsuario );
        pedidosRef = empresaLogadaRef.child("pedidos");


        selected = -1;

        adapter = new AdapterListViewPedidos(pedidos,this);

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
        pedidosRef.removeEventListener( childEventListenerPedidos);

    }
    public void buscarPedidos(){

        childEventListenerPedidos = pedidosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidos.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.child("status").getValue().toString().equals(status) || status.equals("todos")|| (status.equals("Preparando Pedido") && ds.child("status").getValue().toString().equals("Saiu para entrega") )) {
                        pedidos.add(ds.getValue(Pedido.class));
                    }
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