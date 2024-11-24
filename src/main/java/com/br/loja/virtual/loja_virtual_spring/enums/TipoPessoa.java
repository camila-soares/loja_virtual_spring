package com.br.loja.virtual.loja_virtual_spring.enums;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public enum TipoPessoa {

    JURIDICA("Juridica"),
    JURIDICA_FORNECEDOR("Juridica e Fornecedor"),
    FISICA("Fisica");


    private String descricao;
}
