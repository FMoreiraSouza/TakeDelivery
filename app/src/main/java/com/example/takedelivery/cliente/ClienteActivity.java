package com.example.takedelivery.cliente;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.takedelivery.acesso.AcessoCliente;
import com.example.takedelivery.empresa.AdicionarProduto;
import com.example.takedelivery.R;
import com.example.takedelivery.adapter.AdapterListViewEmpresas;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Pedido;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.adapter.AdapterListViewEmpresas;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.firebase.FirebaseOptions;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    Carrinho carrinho = new Carrinho();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Take Delivery Cliente");
//        setSupportActionBar(toolbar);


        String identificadorCli = ClienteFirebase.getIdentificarCliente();
        database = FirebaseOptions.getFirebase();
        cliLogadoRef = database.child("Clientes").child(identificadorCli);
        empresasRef = database.child("Empresas");

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
        intent.putExtra("cliente", cliente);

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cliente, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.config :
                abrirConfiguracoes();
                break;
            case R.id.addshopcar :
                verCarrinho();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void deslogarUsuario(){
        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

//    public void verCardapio (View view){
//        Intent intent = new Intent(this, CardapioActivity.class);
//
//        startActivity(intent);
//
//    }


    private void abrirConfiguracoes(){
        startActivity(new Intent(ClienteActivity.this, ConfigurarCliente.class));
    }

    private void abrirNovoProduto(){
        startActivity(new Intent(ClienteActivity.this, AdicionarProduto.class));
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
        if(carrinho!= null) {
            Intent intent = new Intent(this, CarrinhoActivity.class);
            intent.putExtra("carrinho", carrinho);
            intent.putExtra("cliente", cliente);

            startActivity(intent);
        }else{
            Toast.makeText(ClienteActivity.this,
                    "Carrinho vazio",
                    Toast.LENGTH_SHORT).show();

        }
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
    public void buscarEmpresas(){

        valueEventListenerEmpresas = empresasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                empresas.clear();
                ArrayList<Produto> produtos = new ArrayList();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot pro : ds.child("produtos").getChildren()) {
                        produtos.add(pro.getValue(Produto.class));
                    }
                    Empresa empresa = new Empresa();
                    empresa.setProdutos(produtos);
                    empresa.setId(ds.getKey());
                    empresa.setNome(ds.child("nome").getValue().toString());
                    empresa.setEmail(ds.child("email").getValue().toString());
                    empresa.setNome(ds.child("senha").getValue().toString());
                    empresa.setCnpj(ds.child("cnpj").getValue().toString());
                    empresa.setNomeFantasia(ds.child("nomeFantasia").getValue().toString());
                    empresa.setTelefone(ds.child("telefone").getValue().toString());
                    empresa.setCep(ds.child("cep").getValue().toString());
                    empresa.setEstado(ds.child("estado").getValue().toString());
                    empresa.setCidade(ds.child("cidade").getValue().toString());
                    empresa.setBairro(ds.child("bairro").getValue().toString());
                    empresa.setEndereco(ds.child("endereco").getValue().toString());
                    empresas.add(empresa);

                }
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
                ArrayList<Pedido> pedidos = new ArrayList();

                for (DataSnapshot ped : dataSnapshot.child("pedidos").getChildren()) {
                    pedidos.add(ped.getValue(Pedido.class));
                }
                cliente = new Cliente();
                cliente.setPedidos(pedidos);
                cliente.setID(dataSnapshot.getKey());

                cliente.setNome(dataSnapshot.child("nome").getValue().toString());
                cliente.setEmail(dataSnapshot.child("email").getValue().toString());
                cliente.setSenha(dataSnapshot.child("senha").getValue().toString());
                cliente.setTelefone(dataSnapshot.child("telefone").getValue().toString());
                cliente.setCidade(dataSnapshot.child("cidade").getValue().toString());
                cliente.setBairro(dataSnapshot.child("bairro").getValue().toString());
                cliente.setEndereco(dataSnapshot.child("endereco").getValue().toString());
                cliente.setUrlImagem(dataSnapshot.child("urlImagem").exists()? dataSnapshot.child("urlImagem").getValue().toString(): "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}