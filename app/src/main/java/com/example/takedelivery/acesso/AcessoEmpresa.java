package com.example.takedelivery.acesso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takedelivery.empresa.CadastroInicialEmpresa;
import com.example.takedelivery.empresa.EmpresaActivity;
import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.model.AcessoEmpresaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class AcessoEmpresa extends AppCompatActivity {

    private EditText Email, Senha;
    private FirebaseAuth autenticar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acesso_empresa);
        autenticar = FirebaseOptions.getFirebaseAutenticacao();

        Email = findViewById(R.id.writeEmailEmpresa);
        Senha = findViewById(R.id.writeSenhaEmpresa);


    }

    public void logarUsuario(AcessoEmpresaActivity.Empresa usuario) {

        autenticar.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
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

                AcessoEmpresaActivity.Empresa usuario = new AcessoEmpresaActivity.Empresa();
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

    public void abrirTelaCadastro(View view) {
        Intent intent = new Intent(AcessoEmpresa.this, CadastroInicialEmpresa.class);
        startActivity(intent);
    }

    public void abrirTelaPrincipal() {
        Intent intent = new Intent(AcessoEmpresa.this, EmpresaActivity.class);
        startActivity(intent);
    }


}