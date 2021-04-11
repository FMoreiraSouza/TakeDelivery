package com.example.takedelivery.cliente;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.firebase.ClienteFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Carrinho;
import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DatabaseReference;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class AdicionaProdutoCarrinhoActivity extends AppCompatActivity {
    Empresa empresa;
    Produto produto;
    Cliente cliente;

    TextView editTextNome;
    TextView editTextDescricao;
    TextView editTextPreco;
    EditText editTextQtd;

    private DatabaseReference database;
    private DatabaseReference cliLogadoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_produto_carrinho);

        empresa = (Empresa) getIntent().getSerializableExtra("empresa");
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        produto = (Produto) getIntent().getSerializableExtra("produto");


        editTextNome = findViewById( R.id.textViewNomePro );
        editTextDescricao = findViewById( R.id.textViewDescricaoPro );
        editTextPreco = findViewById( R.id.textViewPrecoPro );

        editTextNome.setText(produto.getNome());
        editTextDescricao.setText(produto.getDescricao());
        Locale ptBr = new Locale("pt", "BR");
        editTextPreco.setText( NumberFormat.getCurrencyInstance(ptBr).format(produto.getPreco()));

        String identificadorCli = ClienteFirebase.getIdentificarCliente();
        database = FirebaseItems.getFirebaseDatabase();
        cliLogadoRef = database.child("clientes")
                .child( identificadorCli );


    }

    public void addProCarrinho( View view ){
        Intent intent = new Intent(this, CarrinhoActivity.class);

        editTextQtd = findViewById( R.id.editTexQtd );
        String qtd = editTextQtd.getText().toString();
        Float quantidade = new Float(qtd);
        Float valorTotal = produto.getPreco() * quantidade;

        Carrinho carrinho = new Carrinho();
        carrinho.setEmpresa(empresa);
        carrinho.setValorTotal(valorTotal);
        carrinho.setQtde(Integer.parseInt(qtd));
        carrinho.setProduto(produto);
        carrinho.salvar(cliLogadoRef);

        intent.putExtra( "carrinho", carrinho);
        intent.putExtra( "cliente", cliente);


        startActivity(intent);

    }
}