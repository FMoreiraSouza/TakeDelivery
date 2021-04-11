package com.example.takedelivery.model;

import java.io.Serializable;
import java.util.ArrayList;

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
    ArrayList<Produto> produtos;
    ArrayList<Pedido> pedidos;

    String categoria;

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


    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Empresa() {
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void salvar(){
        DatabaseReference firebaseRef = FirebaseItems.getFirebaseDatabase();
        String idEmpresa = firebaseRef.push().getKey();
        setId(idEmpresa);
        DatabaseReference usuario = firebaseRef.child("empresas").child( getId() );
        usuario.setValue( this );
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

    public String getNumero() {
        return numero;
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

    public String getNúmero() {
        return numero;
    }

    public void setNumero(String núuero) {
        this.numero = numero;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
