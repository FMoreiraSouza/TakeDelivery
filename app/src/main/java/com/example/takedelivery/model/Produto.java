package com.example.takedelivery.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Produto implements Serializable {
    String id;
    String nome;
    String descricao;
    Float preco;
    private static int contadorId = 0;


    public Produto(String id, String nome, String descricao, Float preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.id = id;

    }

    public Produto() {
    }

    public void setId(String id) {

        //setId( idGrupoFirebase );
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Float getPreco() {
        return preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco( Float preco) {
        this.preco = preco;
    }

    public void salvar(DatabaseReference empresaRef){
        DatabaseReference produtoRef = empresaRef.child("produtos");

        if(getId() == null){
            String idProdutoFirebase = produtoRef.push().getKey();
            setId(idProdutoFirebase);
        }
        produtoRef.child( getId() ).setValue( this );
    }

    public void excluir(DatabaseReference empresaRef){
        DatabaseReference produtoRef = empresaRef.child("produtos");
        produtoRef.child( getId() ).removeValue();
    }
}

