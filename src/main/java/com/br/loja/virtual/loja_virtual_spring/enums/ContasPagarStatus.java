package com.br.loja.virtual.loja_virtual_spring.enums;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ContasPagarStatus {

    CONBRANCA("Pagar"),
    VENCIDA("Vencida"),
    ABERTA("Aberta"),
    ALUGUEL("Aluguel"),
    FUNCIONARIO("Funcionário"),
    NEGOCIADA("Renegociada"),
    QUITADA("Quitada");

    private String descricao;

    private ContasPagarStatus(String descricao) {
        this.descricao = descricao;
    }
}
