package com.example.takedelivery.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takedelivery.empresa.CadastroEmpresa;
import com.example.takedelivery.firebase.CryptografiaBase64;
import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.model.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroInicialCliente extends AppCompatActivity {

    private EditText Nome, Email, Senha, Telefone, Endereco, Bairro, Cidade;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_cliente);

        Nome = findViewById(R.id.editNomeCli);
        Email = findViewById(R.id.editEmailCli);
        Senha = findViewById(R.id.editSenhaCli);
        Telefone = findViewById(R.id.editTelefoneCli);
        //Endereco = findViewById(R.id.editEnderecoCli);
        //Bairro = findViewById(R.id.editBairroCli);
        //Cidade = findViewById(R.id.editCidadeCli);


    }

    public void cadastrarUsuario(Cliente cliente){

        autenticacao = FirebaseOptions.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                cliente.getEmail(), cliente.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    Toast.makeText(CadastroInicialCliente.this, "Sucesso ao cadastrar",
                            Toast.LENGTH_SHORT).show();
                    finish();

                    try {

                        String identificadorUsuario = CryptografiaBase64.codificarBase64( cliente.getEmail() );
                        cliente.setID( identificadorUsuario );
                        cliente.salvarCliente();
                        continuarCadastroCliente(cliente);

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Digite um e-mail válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta existe";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroInicialCliente.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }

        });
    }

    public void validarCadastroUsuario(View view){

        String textoNome  = Nome.getText().toString();
        String textoEmail = Email.getText().toString();
        String textoSenha = Senha.getText().toString();
        String textoTelefone = Telefone.getText().toString();
        //String textoEndereco  = Endereco.getText().toString();
        //String textoBairro = Bairro.getText().toString();
        //String textoCidade = Senha.getText().toString();

        if( !textoNome.isEmpty() ){
            if( !textoEmail.isEmpty() ){
                if ( !textoSenha.isEmpty() ){

                    Cliente usuario = new Cliente();
                    usuario.setNome( textoNome );
                    usuario.setEmail( textoEmail );
                    usuario.setSenha( textoSenha );
                    usuario.setTelefone( textoTelefone );
                    //usuario.setEndereco(textoEndereco);
                    //usuario.setBairro( textoBairro );
                    //usuario.setCidade( textoCidade);


                    cadastrarUsuario( usuario );

                }else {
                    Toast.makeText(CadastroInicialCliente.this,
                            "Preencha a senha",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CadastroInicialCliente.this,
                        "Preencha o email",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CadastroInicialCliente.this,
                    "Preencha o nome",
                    Toast.LENGTH_SHORT).show();
        }

    }


    public void continuarCadastroCliente(Cliente usuario){
        Intent intent = new Intent( this, CadastroCliente.class );
        intent.putExtra("cliente", usuario);
        startActivity(intent);
    }


}