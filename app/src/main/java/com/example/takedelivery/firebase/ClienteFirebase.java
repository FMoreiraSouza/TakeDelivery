package com.example.takedelivery.firebase;

import com.example.takedelivery.model.Cliente;
import com.example.takedelivery.model.Empresa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClienteFirebase {
    public static String getIdentificarCliente(){

        FirebaseAuth empresa = FirebaseItems.getFirebaseAutenticacao();
        String email = empresa.getCurrentUser().getEmail();
        String identificarCliente = CryptografiaBase64.codificarBase64( email );

        return identificarCliente;

    }

    public static FirebaseUser getClienteAtual(){
        FirebaseAuth cliente = FirebaseItems.getFirebaseAutenticacao();

        return cliente.getCurrentUser();
    }

    public static Cliente getDadosEmpresaLogado(){

        FirebaseUser firebaseUser = getClienteAtual();

        Cliente cliente = new Cliente();
        cliente.setEmail( firebaseUser.getEmail() );
        cliente.setNome( firebaseUser.getDisplayName() );

        return cliente;

    }
}
