package com.example.takedelivery.cliente;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.empresa.PedidosEmpresaActivity;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Pedido;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class ResumoPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pedido);

        Pedido pedido = (Pedido) getIntent().getSerializableExtra("pedido");
//        TextView data = (TextView) findViewById(R.id.textViewDataRes);
        TextView nomeCliente = (TextView) findViewById(R.id.textViewNomeClienteRes);
        TextView endereco = (TextView) findViewById(R.id.textViewEnderecoRes);
        TextView bairro = (TextView) findViewById(R.id.textViewBairroRes);
        TextView valor = (TextView) findViewById(R.id.textViewValorPedRes);
        TextView qtd = (TextView) findViewById(R.id.textViewqtdProRes);
        TextView produto = (TextView) findViewById(R.id.textViewProRes);
        TextView preco = (TextView) findViewById(R.id.textViewPrecoRes);
        TextView pagamento = (TextView) findViewById(R.id.textViewPagamentoRes);

        nomeCliente.setText(pedido.getCliente().getNome());
        endereco.setText(pedido.getCliente().getEndereco());
        bairro.setText(pedido.getCliente().getBairro());
        produto.setText(pedido.getProduto().getNome());
        qtd.setText(String.valueOf(pedido.getQtd()) + "x");
        Locale ptBr = new Locale("pt", "BR");
        preco.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getProduto().getPreco()));
        valor.setText( NumberFormat.getCurrencyInstance(ptBr).format(pedido.getValorTotal()));
//        data.setText(pedido.getData());
        pagamento.setText(pedido.getMetodoDePagamento());

    }

    public void voltarTelaInicial(View view){
        Intent intent = new Intent(this, ClienteActivity.class);
        startActivity(intent);
    }
}