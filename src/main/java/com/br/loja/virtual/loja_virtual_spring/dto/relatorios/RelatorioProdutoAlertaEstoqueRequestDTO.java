package com.br.loja.virtual.loja_virtual_spring.dto.relatorios;


import lombok.Data;

import java.io.Serializable;

@Data
public class RelatorioProdutoAlertaEstoqueRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    private String nomeProduto="";
    private String nomeFornecedor="";
    private String dataInicial;
    private String dataFinal;
    private String codigoNota="";
    private String quantidadeEstoque;
    private String alertaEstoque;
    private String codigoProduto="";

}
