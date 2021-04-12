package com.example.takedelivery.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.takedelivery.firebase.FirebaseOptions;
import com.example.takedelivery.firebase.FirebaseItems;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Empresa implements Serializable {
    String id;
    String email;
    String nome;
    String senha;
    String cnpj;
    String nomeFantasia;
    String telefone;
    String cep;
    String estado;
    String cidade;
    String bairro;
    String endereco;
    String numero;
    String categoria;
    private String tempo;
    private Double precoEntrega;
    private String urlImagem;
    ArrayList<Produto> produtos;
    ArrayList<Pedido> pedidos;


//    public Empresa(String id, String email, String nome, String senha, String cnpj, String nomeFantasia, String telefone, String cep, String estado, String cidade, String bairro, String endereco, String numero, ArrayList<Produto> cardapio, String categoria) {
//        this.id = id;
//        this.email = email;
//        this.nome = nome;
//        this.senha = senha;
//        this.cnpj = cnpj;
//        this.nomeFantasia = nomeFantasia;
//        this.telefone = telefone;
//        this.cep = cep;
//        this.estado = estado;
//        this.cidade = cidade;
//        this.bairro = bairro;
//        this.endereco = endereco;
//        this.numero = numero;
//        this.cardapio = cardapio;
//        this.categoria = categoria;
//    }



    public Empresa(String email, String nome, String senha) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
    }

    public Empresa() {
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void salvarEmpresa(){
        DatabaseReference firebaseRef = FirebaseOptions.getFirebase();
        DatabaseReference usuario = firebaseRef.child("Empresas").child( getId() );
        usuario.setValue( this );
    }
    public void addImagem(){
        DatabaseReference firebaseRef = FirebaseOptions.getFirebase();
        firebaseRef.child("Empresas").child( getId() ).child("urlImagem").setValue(getUrlImagem());
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Double getPrecoEntrega() {
        return precoEntrega;
    }

    public void setPrecoEntrega(Double precoEntrega) {
        this.precoEntrega = precoEntrega;
    }


    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }


    @Exclude

    public String getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

//    public ArrayList<Produto> getCardapio() {
//        return cardapio;
//    }
//
//    public void setCardapio(ArrayList<Produto> cardapio) {
//        this.cardapio = cardapio;
//    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
