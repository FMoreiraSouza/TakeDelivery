package com.example.takedelivery.cliente;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Carrinho;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Pedido;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    Cliente cliente;

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

        carrinho = (Carrinho) getIntent().getSerializableExtra("carrinho");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");


        textViewNomeEmpresa = findViewById( R.id.textViewNomeEmpresa );
        textViewEndereco = findViewById( R.id.textViewEnderecoEntrega );
        textViewNome = findViewById( R.id.textViewNomeProduto );
        textViewPreco = findViewById( R.id.textViewPrecoProduto );
        textViewTotal = findViewById( R.id.textViewTotal );
        textViewtQtd = findViewById( R.id.textViewQtd);

        if(carrinho != null) {
            textViewNomeEmpresa.setText(carrinho.getEmpresa().getNomeFantasia());
            textViewEndereco.setText(carrinho.getEmpresa().getEndereco());
            textViewNome.setText(carrinho.getProduto().getNome());
            textViewtQtd.setText(String.valueOf(carrinho.getQtde()));
            Locale ptBr = new Locale("pt", "BR");
            textViewPreco.setText(NumberFormat.getCurrencyInstance(ptBr).format(carrinho.getProduto().getPreco()));
            textViewTotal.setText(NumberFormat.getCurrencyInstance(ptBr).format(carrinho.getValorTotal()));

            String identificadorCli = ClienteFirebase.getIdentificarCliente();
            database = FirebaseItems.getFirebaseDatabase();
            cliLogadoRef = database.child("clientes")
                    .child(identificadorCli);

            carrinhoRef = cliLogadoRef.child("carrinho");
            empresaRef = database.child("empresas")
                    .child(carrinho.getEmpresa().getId());
        }
    }
    public void limparCarrinho(View view) {
        Intent intent = new Intent(this, CardapioActivity.class);
        startActivity(intent);

    }
    public void fazerPedido(View view) {
        Intent intent = new Intent(this, ResumoPedidoActivity.class);
        Pedido pedido = new Pedido();
        pedido.setEmpresa(carrinho.getEmpresa());
        pedido.setCliente(cliente);
        pedido.setStatus("Pendente Aprovação");
        pedido.setProduto(carrinho.getProduto());
        Date data = new Date();
        pedido.setData(new SimpleDateFormat("dd-MM-yyyy").format(data));
        pedido.setMetodoDePagamento("Dinheiro");
        pedido.setHora( new SimpleDateFormat("HH:mm:ss").format(data));
        pedido.setQtd(carrinho.getQtde());
        pedido.setValorTotal(carrinho.getValorTotal());
        pedido.salvar(cliLogadoRef, empresaRef);

        carrinhoRef.removeValue();
        Toast.makeText(CarrinhoActivity.this, "Pedido realizado",
                Toast.LENGTH_SHORT).show();
        intent.putExtra("pedido", pedido);
        startActivity(intent);

    }

    public void cancelarPedido(View view){
        Intent intent = new Intent(this, ClienteActivity.class);
        Toast.makeText(CarrinhoActivity.this, "Pedido Cancelado",
                Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }


}