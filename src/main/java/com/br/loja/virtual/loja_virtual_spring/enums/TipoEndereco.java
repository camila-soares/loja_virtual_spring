package com.br.loja.virtual.loja_virtual_spring.enums;


import lombok.ToString;

@ToString
public enum TipoEndereco {

    COBRANCA("Cobrança"),
    ENTREGA("Entrega");

    private String descricao;

     TipoEndereco(String descricao) {
         this.descricao = descricao;
    }
}
