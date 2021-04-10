package com.example.takedelivery.cliente;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Carrinho;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.example.takedelivery.model.Pedido;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CarrinhoActivity extends AppCompatActivity {

    public static Empresa empresa;
    public static Produto produto;

    TextView editTextQtd;
    TextView editTextNome;
    TextView editTextPreco;
    TextView editTextTotal;
    TextView textViewNomeEmpresa;
    TextView textViewEndereco;


    String qtd;
    String nome ;
    Float preco ;
    Float total ;
    String nomeEmpresa;
    private ValueEventListener valueEventListenerCarrinho;
    private ValueEventListener valueEventListenerCliente;

    private DatabaseReference database;
    private DatabaseReference carrinhoRef;
    private DatabaseReference cliLogadoRef;
    private DatabaseReference empresaRef;
    public static Carrinho carrinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        empresa = CarrinhoActivity.empresa;
        produto = CarrinhoActivity.produto;
        carrinho = CarrinhoActivity.carrinho;
        editTextQtd = findViewById( R.id.textViewQtd);
        editTextNome = findViewById( R.id.textViewNomeProduto );
        editTextPreco = findViewById( R.id.textViewPrecoProduto );
        editTextTotal = findViewById( R.id.textViewTotal );
        textViewNomeEmpresa = findViewById( R.id.textViewNomeEmpresa );
        textViewEndereco = findViewById( R.id.textViewEnderecoEntrega );

        String identificadorCli = ClienteFirebase.getIdentificarCliente();
        database = FirebaseItems.getFirebaseDatabase();
        cliLogadoRef = database.child("clientes")
                .child( identificadorCli );

        carrinhoRef = cliLogadoRef.child("carrinho");

        empresaRef = database.child("empresas")
                .child( carrinho.getEmpresa().getId() );

            nome = (String) getIntent().getExtras().get( "nome" );
            qtd = String.valueOf(carrinho.getQtde()) + "x";

            preco = (Float) getIntent().getExtras().get( "preco" );
            total = (Float) getIntent().getExtras().get( "precoTotal" );
            nomeEmpresa = empresa.getNomeFantasia();

            textViewNomeEmpresa.setText(nomeEmpresa);
            editTextNome.setText(nome);
            editTextQtd.setText(qtd);
            Locale ptBr = new Locale("pt", "BR");
            editTextPreco.setText( NumberFormat.getCurrencyInstance(ptBr).format(preco));
            editTextTotal.setText( NumberFormat.getCurrencyInstance(ptBr).format(total));



    }
    public void limparCarrinho(View view) {
        editTextNome.setText("");
        editTextPreco.setText("");
        editTextTotal.setText("");
        Intent intent = new Intent(this, CardapioActivity.class);
        startActivity(intent);

    }
    public void fazerPedido(View view) {
        Intent intent = new Intent(this, ClienteActivity.class);
        Pedido pedido = new Pedido();
        pedido.setEmpresa(carrinho.getEmpresa());
        pedido.setCliente(cliLogadoRef.getKey());
        pedido.setStatus("Pendente Aprovação");
        pedido.setData(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) );
        pedido.setMetodoDePagamento("Dinheiro");
        pedido.setHora("15");
        pedido.setQtd(carrinho.getQtde());
        pedido.salvar(cliLogadoRef, empresaRef);

        carrinhoRef.removeValue();
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        buscarProdutos();

    }

    @Override
    public void onStop() {
        super.onStop();
        carrinhoRef.removeEventListener( valueEventListenerCarrinho );
    }

    public void buscarProdutos() {

        valueEventListenerCarrinho = carrinhoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                carrinho = dataSnapshot.getValue(Carrinho.class);
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