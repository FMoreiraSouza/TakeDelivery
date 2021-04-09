package com.example.takedelivery;

import java.util.List;

public class Pedido {
    private static int contadorId = 0;

    int id;
    String idCliente;
    String idRestaurante;
    String data;
    int hora;
    String metodoDePagamento;
    String status;
    List<String> produtos;
    Float valorTotal;
    public Pedido() {
    }

    public Pedido(int id, String idCliente, String idRestaurante, String data, int hora, String metodoDePagamento, String status, List<String> produtos, Float valorTotal) {
        this.id = id;
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.data = data;
        this.hora = hora;
        this.metodoDePagamento = metodoDePagamento;
        this.status = status;
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public int getId() {
        return id;
    }

    public String  getStatus() {
        return status;
    }

//    public void setStatus(StatusPedido status) {
//        this.status = status;
//    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHora() {
        return hora;
    }



    public String getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(String metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }


}
