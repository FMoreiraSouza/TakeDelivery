package com.example.takedelivery;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CadastroEmpresa extends AppCompatActivity {

    EditText editTextCnpj;
    EditText editTextNomeFantasia;
    EditText editTextTelefone;
    EditText editTextCep;
    EditText editTextEstado;
    EditText editTextCidade;
    EditText editTextBairro;
    EditText editTextEndereco;
    EditText editTextNumero;

    ArrayList<EstruturaEmpresa> estruturaEmpresas = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance ();
    private DatabaseReference mDatabaseReference = mDatabase.getReference ();
    boolean edit;
    int idProdutoEditar;
    String selecteditem;
    public static EstruturaEmpresa estruturaEmpresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);




        estruturaEmpresa = CadastroEmpresa.estruturaEmpresa;

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        List<String> categorias = new ArrayList<String>();

        for (Categoria categoria: Categoria.values()){
            categorias.add(categoria.getDescricao());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                selecteditem =  adapter.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {

            }
        });
        //getEmpresas();
    }

//    public void cancelar( View view ){
//        setResult( Constants.RESULT_CANCEL );
//        setResult( Constants.RESULT_CANCEL );
//        finish();
//    }
//


    public void cadastrar( View view ){
        editTextCnpj = findViewById( R.id.editTextCNPJ );
        editTextNomeFantasia = findViewById( R.id.editTextNomeFantasia );
        editTextTelefone = findViewById( R.id.editTextTelefone );
        editTextCep = findViewById( R.id.editTextCEP );
        editTextEstado = findViewById( R.id.editTextEstado );
        editTextCidade = findViewById( R.id.editTextCidade );
        editTextBairro = findViewById( R.id.editTextBairro );
        editTextEndereco = findViewById( R.id.editTextEndereco );
        editTextNumero = findViewById( R.id.editTextNumero );

        String cnpj = editTextCnpj.getText().toString();;
        String nomeFantasia = editTextNomeFantasia.getText().toString();;
        String telefone = editTextTelefone.getText().toString();;
        String cep = editTextCep.getText().toString();;
        String estado = editTextEstado.getText().toString();;
        String cidade = editTextCidade.getText().toString();;
        String bairro = editTextBairro.getText().toString();;
        String endereco = editTextEndereco.getText().toString();;
        String numero = editTextNumero.getText().toString();

        estruturaEmpresa.setCnpj(cnpj);
        estruturaEmpresa.setNomeFantasia(nomeFantasia);
        estruturaEmpresa.setTelefone(telefone);
        estruturaEmpresa.setCep(cep);
        estruturaEmpresa.setEstado(estado);
        estruturaEmpresa.setCidade(cidade);
        estruturaEmpresa.setBairro(bairro);
        estruturaEmpresa.setEndereco(endereco);
        estruturaEmpresa.setNumero(numero);

        estruturaEmpresa.salvarEmpresa();

        Toast.makeText(CadastroEmpresa.this, "Sucesso ao cadastrar",
                Toast.LENGTH_SHORT).show();
        paginaEmpresa();
        //new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                paginaEmpresa();
//            }
//        }, 5000);
    }
    private void paginaEmpresa() {
        Intent intent = new Intent( this, AcessoEmpresa.class );
        startActivity(intent);
    }

}