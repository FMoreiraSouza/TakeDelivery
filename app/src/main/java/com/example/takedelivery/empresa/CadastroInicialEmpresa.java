package com.example.takedelivery.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.R;
import com.example.takedelivery.model.AcessoEmpresaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.example.takedelivery.firebase.CryptografiaBase64;

public class CadastroInicialEmpresa extends AppCompatActivity {

    EditText editTextNome, editTextEmail, editTextSenha;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_empresa);

        editTextNome = findViewById(R.id.editNomeResp);
        editTextEmail = findViewById(R.id.editEmailEmp);
        editTextSenha = findViewById(R.id.editSenhaEmp);
    }

    public void cadastrarUsuario(AcessoEmpresaActivity.Empresa empresa){

        autenticacao = FirebaseOptions.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                empresa.getEmail(), empresa.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
//                    Toast.makeText(CadastroInicialEmpresaActivity.this, "Sucesso ao cadastrar",
//                            Toast.LENGTH_SHORT).show();
//                    finish();
                    continuarCadastro(empresa);
                    try {
                        String identificadorUsuario = CryptografiaBase64.codificarBase64( empresa.getEmail() );
                        empresa.setId( identificadorUsuario );
                        empresa.salvarEmpresa();

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
                    Toast.makeText(CadastroInicialEmpresa.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }

        });
    }

    public void validarCadastroEmpresa(View view){

        String textoNome  = editTextNome.getText().toString();
        String textoEmail = editTextEmail.getText().toString();
        String textoSenha = editTextSenha.getText().toString();

        if( !textoNome.isEmpty() ){
            if( !textoEmail.isEmpty() ){
                if ( !textoSenha.isEmpty() ){

                    AcessoEmpresaActivity.Empresa empresa = new AcessoEmpresaActivity.Empresa();
                    empresa.setNome( textoNome );
                    empresa.setEmail( textoEmail );
                    empresa.setSenha( textoSenha );

                    cadastrarUsuario( empresa );

                }else {
                    Toast.makeText(CadastroInicialEmpresa.this,
                            "Preencha a senha",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CadastroInicialEmpresa.this,
                        "Preencha o email",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CadastroInicialEmpresa.this,
                    "Preencha o nome",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void continuarCadastro(AcessoEmpresaActivity.Empresa empresa){
        Intent intent = new Intent( this, CadastroEmpresa.class );
        CadastroEmpresa.empresa = empresa;
        startActivity(intent);
    }


}