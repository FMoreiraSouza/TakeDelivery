package com.example.takedelivery;

import com.example.takedelivery.model.Empresa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmpresaFirebase {
    public static String getIdentificarEmpresa(){

        FirebaseAuth empresa = FirebaseOptions.getFirebaseAutenticacao();
        String email = empresa.getCurrentUser().getEmail();
        String identificarEmpresa = CryptografiaBase64.codificarBase64( email );

        return identificarEmpresa;

    }

    public static FirebaseUser getEmpresaAtual(){
        FirebaseAuth usuario = FirebaseOptions.getFirebaseAutenticacao();

        return usuario.getCurrentUser();
    }

    public static Empresa getDadosEmpresaLogado(){

        FirebaseUser firebaseUser = getEmpresaAtual();

        Empresa empresa = new Empresa();
        empresa.setEmail( firebaseUser.getEmail() );
        empresa.setNome( firebaseUser.getDisplayName() );

        return empresa;

    }

}
