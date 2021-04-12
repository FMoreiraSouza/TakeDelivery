package com.example.takedelivery.firebase;

import android.util.Base64;

import com.example.takedelivery.model.Empresa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CryptografiaBase64 {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(),
                Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decodificarBase64(String textoCodificado){
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }

    public static class EmpresaFirebase {
        public static String getIdentificarEmpresa(){

            FirebaseAuth empresa = FirebaseOptions.getFirebaseAutenticacao();
            String email = empresa.getCurrentUser().getEmail();
            String identificarEmpresa = codificarBase64( email );

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

}
