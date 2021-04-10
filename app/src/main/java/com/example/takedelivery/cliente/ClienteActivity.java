package com.example.takedelivery.cliente;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.adapter.AdapterListViewEmpresas;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Carrinho;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {

    ArrayList<Produto> cardapio = new ArrayList<Produto>();

    ArrayList<Empresa> empresas = new ArrayList<Empresa>();
    int selected;
    AdapterListViewEmpresas adapter;

    ListView listViewEmpresas;
    private DatabaseReference database;
    private DatabaseReference cliLogadoRef;
    private DatabaseReference empresasRef;
    private ValueEventListener valueEventListenerEmpresas;
    private ValueEventListener valueEventListenerCliente;
    Cliente cliente;
    Carrinho carrinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


        String identificadorCli = ClienteFirebase.getIdentificarCliente();
        database = FirebaseItems.getFirebaseDatabase();
        cliLogadoRef = database.child("clientes")
                .child( identificadorCli );
        empresasRef = database.child("empresas");

        selected = -1;
        adapter = new AdapterListViewEmpresas(empresas, this);

        listViewEmpresas = (ListView) findViewById(R.id.listViewRestaurantes);

        listViewEmpresas.setAdapter(adapter);

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
        intent.putExtra("empresa", empresas.get(selected));
        CardapioActivity.cliente = cliente;


        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_client, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected( MenuItem item ) {

        switch(item.getItemId())
        {
            case R.id.carrinho:
                verCarrinho();
                break;
//            case R.id.editar:
//                editarProduto();
//                break;
//            case R.id.excluir:
//                excluirProduto();
//                break;

        }
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        buscarEmpresas();
        buscarCliente();
    }

    @Override
    public void onStop() {
        super.onStop();
        empresasRef.removeEventListener( valueEventListenerEmpresas );
        cliLogadoRef.removeEventListener( valueEventListenerCliente );

    }

    public void verCarrinho(){
        cliLogadoRef.child("carrinho").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carrinho = dataSnapshot.getValue(Carrinho.class);
                abrirCarrinho();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void abrirCarrinho(){
        Intent intent = new Intent(this, CarrinhoActivity.class);
        CarrinhoActivity.carrinho = carrinho;
//        CarrinhoActivity.empresa = empresa;


        startActivity(intent);
    }
    public void buscarEmpresas(){

        valueEventListenerEmpresas = empresasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empresas.clear();
                ArrayList<Produto> produtos = new ArrayList();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getChildrenCount() > 5) {
                        for (DataSnapshot pro : ds.child("produtos").getChildren()) {
                            produtos.add(pro.getValue(Produto.class));
                        }
                        Empresa empresa = new Empresa();
                        empresa.setProdutos(produtos);
                        empresa.setId(ds.getKey());
                        empresa.setCnpj(ds.child("cnpj").getValue().toString());
                        empresa.setNomeFantasia(ds.child("nomeFantasia").getValue().toString());
                        empresa.setTelefone(ds.child("telefone").getValue().toString());
                        empresa.setCep(ds.child("cep").getValue().toString());
                        empresa.setEstado(ds.child("estado").getValue().toString());
                        empresa.setCidade(ds.child("cidade").getValue().toString());
                        empresa.setBairro(ds.child("bairro").getValue().toString());
                        empresa.setEndereco(ds.child("endereco").getValue().toString());
//                        empresa.setNumero(ds.child("numero").getValue().toString());
                        empresas.add(empresa);

//                        empresas.add(ds.getValue(Empresa.class));

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
    public void buscarCliente() {

        valueEventListenerCliente = cliLogadoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cliente = dataSnapshot.getValue(Cliente.class);
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