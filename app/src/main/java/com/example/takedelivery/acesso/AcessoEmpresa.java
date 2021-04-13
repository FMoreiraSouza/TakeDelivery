package com.example.takedelivery.acesso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takedelivery.empresa.CadastroInicialEmpresa;
import com.example.takedelivery.empresa.EmpresaActivity;
import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.model.Empresa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
public class AcessoEmpresa extends AppCompatActivity {

    private EditText Email, Senha;
    private FirebaseAuth autenticar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_empresa);
        autenticar = FirebaseOptions.getFirebaseAutenticacao();
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Take Delivery Empresa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Email = findViewById(R.id.writeEmailEmpresa);
        Senha = findViewById(R.id.writeSenhaEmpresa);


    }

    public void logarUsuario(Empresa usuario) {

        autenticar.signInWithEmailAndPassword(
                usuario.getEmail(),  usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    abrirTelaEmpresa();
                } else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuário não cadastrado.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Email e senha não correspondem";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(AcessoEmpresa.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validarAutenticacaoUsuario(View view) {

        String textoEmail = Email.getText().toString();
        String textoSenha = Senha.getText().toString();

        if (!textoEmail.isEmpty()) {
            if (!textoSenha.isEmpty()) {

                Empresa usuario = new Empresa();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);

            } else {
                Toast.makeText(AcessoEmpresa.this,
                        "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AcessoEmpresa.this,
                    "Preencha o email!",
                    Toast.LENGTH_SHORT).show();
        }

    }
//
    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autenticar.getCurrentUser();
        if ( usuarioAtual != null ){
            abrirTelaEmpresa();
        }
    }*/

    public void abrirTelaCadastro(View view) {
        Intent intent = new Intent(AcessoEmpresa.this, CadastroInicialEmpresa.class);
        startActivity(intent);
    }

    public void abrirTelaEmpresa() {
        Intent intent = new Intent(AcessoEmpresa.this, EmpresaActivity.class);
        startActivity(intent);
    }


}
