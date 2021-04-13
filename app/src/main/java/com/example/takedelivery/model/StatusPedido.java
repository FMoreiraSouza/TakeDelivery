package com.example.takedelivery.model;

public enum StatusPedido {
    SAIU_PARA_ENTREGA("Saiu para entrega"),
    PREPARANDO("Preparando Pedido"),
    FINALIZADO("Finalizado"),
    PENDENTE("Pendente aprovação");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
