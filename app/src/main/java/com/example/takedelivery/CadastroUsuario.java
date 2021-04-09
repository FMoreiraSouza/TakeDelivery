package com.example.takedelivery;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

/*public class CadastroUsuario extends AppCompatActivity {

    private TextInputEditText Nome, Email, Senha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        Nome = findViewById(R.id.editNome);
        Email = findViewById(R.id.editEmail);
        Senha = findViewById(R.id.editSenha);

    }

    public void cadastrarUsuario(UsuarioApp usuario){

        autenticacao = FirebaseItems.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    Toast.makeText(com.example.contentchat.CadastroUsuario.this, "Sucesso ao cadastrar",
                            Toast.LENGTH_SHORT).show();
                    finish();

                    try {

                        String identificadorUsuario = CryptographyBase64.codificarBase64( usuario.getEmail() );
                        usuario.setID( identificadorUsuario );
                        usuario.salvar();

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
                    Toast.makeText(com.example.contentchat.CadastroUsuario.this,
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

        if( !textoNome.isEmpty() ){
            if( !textoEmail.isEmpty() ){
                if ( !textoSenha.isEmpty() ){

                    UsuarioApp usuario = new UsuarioApp();
                    usuario.setNome( textoNome );
                    usuario.setEmail( textoEmail );
                    usuario.setSenha( textoSenha );

                    cadastrarUsuario( usuario );

                }else {
                    Toast.makeText(com.example.contentchat.CadastroUsuario.this,
                            "Preencha a senha",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(com.example.contentchat.CadastroUsuario.this,
                        "Preencha o email",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(com.example.contentchat.CadastroUsuario.this,
                    "Preencha o nome",
                    Toast.LENGTH_SHORT).show();
        }

    }


}*/