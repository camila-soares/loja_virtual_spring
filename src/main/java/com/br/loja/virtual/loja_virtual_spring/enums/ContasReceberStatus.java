package com.br.loja.virtual.loja_virtual_spring.enums;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ContasReceberStatus {

    CONBRANCA("Pagar"),
    VENCIDA("Vencida"),
    ABERTA("Aberta"),
    QUITADA("Quitada");

    private String descricao;

    private ContasReceberStatus(String descricao) {
        this.descricao = descricao;
    }
}
