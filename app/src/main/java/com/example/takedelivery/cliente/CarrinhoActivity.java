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

    TextView textViewNomeEmpresa;
    TextView textViewEndereco;
    TextView textViewtQtd;
    TextView textViewNome;
    TextView textViewPreco;
    TextView textViewTotal;



    Carrinho carrinho;

    private ValueEventListener valueEventListenerCarrinho;
    private ValueEventListener valueEventListenerCliente;

    private DatabaseReference database;
    private DatabaseReference carrinhoRef;
    private DatabaseReference cliLogadoRef;
    private DatabaseReference empresaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        Carrinho carrinho = (Carrinho) getIntent().getSerializableExtra("Carrinho");

        textViewNomeEmpresa = findViewById( R.id.textViewNomeEmpresa );
        textViewEndereco = findViewById( R.id.textViewEnderecoEntrega );
        textViewNome = findViewById( R.id.textViewNomeProduto );
        textViewPreco = findViewById( R.id.textViewPrecoProduto );
        textViewTotal = findViewById( R.id.textViewTotal );
        textViewtQtd = findViewById( R.id.textViewQtd);


        textViewNomeEmpresa.setText(carrinho.getEmpresa().getNome());
        textViewEndereco.setText(carrinho.getEmpresa().getEndereco());
        textViewNome.setText(carrinho.getProduto().getNome());
        textViewtQtd.setText(String.valueOf(carrinho.getQtde()));
        Locale ptBr = new Locale("pt", "BR");
        textViewPreco.setText( NumberFormat.getCurrencyInstance(ptBr).format(carrinho.getProduto().getPreco()));
        textViewTotal.setText( NumberFormat.getCurrencyInstance(ptBr).format(carrinho.getValorTotal()));

        String identificadorCli = ClienteFirebase.getIdentificarCliente();
        database = FirebaseItems.getFirebaseDatabase();
        cliLogadoRef = database.child("clientes")
                .child( identificadorCli );

        carrinhoRef = cliLogadoRef.child("carrinho");
        empresaRef = database.child("empresas")
                .child( carrinho.getEmpresa().getId() );

    }
    public void limparCarrinho(View view) {
        Intent intent = new Intent(this, CardapioActivity.class);
        startActivity(intent);

    }
    public void fazerPedido(View view) {
        Intent intent = new Intent(this, ClienteActivity.class);
        Pedido pedido = new Pedido();
        pedido.setEmpresa(carrinho.getEmpresa());
        pedido.setCliente(carrinho.getCliente());
        pedido.setStatus("Pendente Aprovação");
        Date data = new Date();
        pedido.setData(new SimpleDateFormat("dd-MM-yyyy").format(data));
        pedido.setMetodoDePagamento("Dinheiro");
        pedido.setHora( new SimpleDateFormat("HH:mm:ss").format(data));
        pedido.setQtd(carrinho.getQtde());
        pedido.salvar(cliLogadoRef, empresaRef);

        carrinhoRef.removeValue();
        startActivity(intent);

    }




}