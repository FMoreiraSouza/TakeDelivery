package com.example.takedelivery.model;

public enum Categoria {
    BRASILEIRA("Brasileira"),
    LANCHE ("Lanche"),
    JAPONESA ("Japonesa"),
    VEGETARIANA ("Vegetariana"),
    PIZZA ("Pizza");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
