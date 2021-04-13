package com.example.takedelivery.empresa;

import android.content.Intent;
import android.os.Bundle;

import com.example.takedelivery.R;
import com.example.takedelivery.firebase.EmpresaFirebase;
import com.example.takedelivery.firebase.FirebaseItems;
import com.example.takedelivery.model.Empresa;
import com.example.takedelivery.model.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

public class CadastroProdutoActivity extends AppCompatActivity {
    EditText editTextNome;
    EditText editTextDescricao;
    EditText editTextPreco;

    boolean edit;
    String idProdutoEditar;
    int id;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    public static Produto produto;
    private DatabaseReference database;
    private DatabaseReference empresaLogadaRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        String identificadorUsuario = EmpresaFirebase.getIdentificarEmpresa();
        database = FirebaseItems.getFirebaseDatabase();
        empresaLogadaRef = database.child("Empresas")
                .child( identificadorUsuario );

        produto = (Produto) getIntent().getSerializableExtra("produto");

        editTextNome = findViewById( R.id.editTextNome );
        editTextDescricao = findViewById( R.id.editTextDescricao );
        editTextPreco = findViewById( R.id.editTextPreco );

        edit = false;

        if( getIntent().getExtras() != null ){
            editTextNome.setText(produto.getNome());
            editTextDescricao.setText(produto.getDescricao());
            editTextPreco.setText(String.valueOf(produto.getPreco()));

            edit = true;
        }

    }

    public void cancelar( View view ){
        finish();
    }

    public void adicionar( View view ){

        String nome = editTextNome.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String preco = editTextPreco.getText().toString();


        if(edit) {
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(new Float(preco));
            produto.salvar(empresaLogadaRef);
        }else{
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(new Float(preco));
            produto.salvar(empresaLogadaRef);

        }
        finish();
    }

}