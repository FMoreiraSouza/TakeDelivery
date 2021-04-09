package com.example.takedelivery;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class UsuarioApp implements Serializable {

    private String ID;
    private String Nome;
    private String Email;
    private String Senha;

    public UsuarioApp() {
    }

    public void salvar(){

        DatabaseReference firebaseRef = FirebaseOptions.getFirebase();
        DatabaseReference usuario = firebaseRef.child("usuarios").child( getID() );
        usuario.setValue( this );
    }

    @Exclude

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }
}