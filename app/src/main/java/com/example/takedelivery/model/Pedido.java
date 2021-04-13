package com.example.takedelivery.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {
    String id;
    Cliente cliente;
    Empresa empresa;
    String data;
    String hora;
    String metodoDePagamento;
    String status;
    Produto produto;
    int qtd;

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    Float valorTotal;



    public Pedido() {
    }



    public void salvar(DatabaseReference clientRef, DatabaseReference clienteRef){
        DatabaseReference pedidoRefEmp = clientRef.child("pedidos");
        DatabaseReference pedidoRefCli = clienteRef.child("pedidos");

        String idPedidoFirebase = pedidoRefCli.push().getKey();
        setId(idPedidoFirebase);
        pedidoRefEmp.child( getId()).setValue( this );
        pedidoRefCli.child( getId()).setValue( this );

    }

    public void setId(String id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }



    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }



    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Float getValorTotal() {
        return valorTotal;
    }



    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }



    public String getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(String metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }


}
