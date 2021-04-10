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

import java.text.NumberFormat;
import java.util.Locale;

public class AdicionaProdutoCarrinhoActivity extends AppCompatActivity {
    public static Empresa empresa;
    public static Produto produto;
    public static Cliente cliente;

    TextView editTextNome;
    TextView editTextDescricao;
    TextView editTextPreco;
    EditText editTextQtd;

    String nome ;
    String descricao ;
    Float preco ;
    String nomeEmpresa;
    private DatabaseReference database;
    private DatabaseReference cliLogadoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_produto_carrinho);

        empresa = AdicionaProdutoCarrinhoActivity.empresa;
        cliente = AdicionaProdutoCarrinhoActivity.cliente;
        produto = AdicionaProdutoCarrinhoActivity.produto;

        String identificadorCli = ClienteFirebase.getIdentificarCliente();
        database = FirebaseItems.getFirebaseDatabase();
        cliLogadoRef = database.child("clientes")
                .child( identificadorCli );

        editTextNome = findViewById( R.id.textViewNomePro );
        editTextDescricao = findViewById( R.id.textViewDescricaoPro );
        editTextPreco = findViewById( R.id.textViewPrecoPro );

        if( getIntent().getExtras() != null ){
            nome = (String) getIntent().getExtras().get( "nome" );
             descricao = (String) getIntent().getExtras().get( "descricao" );
             preco = (Float) getIntent().getExtras().get( "preco" );
            nomeEmpresa = (String) getIntent().getExtras().get( "nomeEmpresa" );

            editTextNome.setText(nome);
            editTextDescricao.setText(descricao);
            Locale ptBr = new Locale("pt", "BR");
            editTextPreco.setText( NumberFormat.getCurrencyInstance(ptBr).format(preco));
        }
    }

    public void addProCarrinho( View view ){
        Intent intent = new Intent(this, CarrinhoActivity.class);
        CarrinhoActivity.empresa = empresa;
        CarrinhoActivity.produto = produto;
        editTextQtd = findViewById( R.id.editTexQtd );

        String qtd = editTextQtd.getText().toString();
        Float quantidade = new Float(qtd);
        Float valorTotal = preco * quantidade;

        Carrinho carrinho = new Carrinho();
        carrinho.setEmpresa(empresa);
        carrinho.setValorTotal(valorTotal);
        carrinho.setQtde(Integer.parseInt(qtd));
        carrinho.setProduto(produto);
        carrinho.salvar(cliLogadoRef);

        CarrinhoActivity.carrinho = carrinho;

        intent.putExtra("nomeEmpresa", nomeEmpresa );
        intent.putExtra("nome", nome );

        intent.putExtra("preco", preco );
        intent.putExtra("precoTotal",valorTotal);
        startActivity(intent);

    }
}