package com.br.loja.virtual.loja_virtual_spring.enums;

import lombok.*;



@Getter
@ToString
public enum StatusVenda {

    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    ABANDONOU_CARRINHO("Abandonou carrinho");

    private String descricao = "";

    private  StatusVenda(String valor){
        this.descricao = valor;
    }


}
