package com.example.takedelivery.model;

import com.example.takedelivery.firebase.FirebaseItems;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

public class Carrinho implements Serializable {
    String id;
    Cliente cliente;
    Empresa empresa;
    Produto produto;
    Float valorTotal;
    int qtde;


    public void salvar(DatabaseReference clienteRef){
        DatabaseReference carrinhoRef = clienteRef.child("carrinho");
        carrinhoRef.setValue( this );

    }
    public Carrinho() {
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProdutos() {
        return produto;
    }

    public void setProdutos(Produto produto) {
        this.produto = produto;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
