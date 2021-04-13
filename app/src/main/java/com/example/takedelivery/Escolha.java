package com.example.takedelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.takedelivery.acesso.AcessoCliente;
import com.example.takedelivery.acesso.AcessoEmpresa;
import com.example.takedelivery.cliente.CadastroCliente;
import com.example.takedelivery.cliente.CadastroInicialCliente;
import com.google.firebase.auth.FirebaseAuth;

public class Escolha extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        Permissoes.validarPermissoes(permissoes, this, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults){
            if( permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }

    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void abrirAcessoCliente(View view){
        Intent i  = new Intent(Escolha.this, CadastroInicialCliente.class);
        startActivity(i);
        finish();
    }
    public void abrirAcessoEmpresa(View view){
        Intent j  = new Intent(Escolha.this, AcessoEmpresa.class);
        startActivity(j);
        finish();
    }


}