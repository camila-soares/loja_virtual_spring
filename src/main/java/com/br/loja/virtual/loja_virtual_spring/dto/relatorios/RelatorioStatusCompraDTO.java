package com.br.loja.virtual.loja_virtual_spring.dto.relatorios;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RelatorioStatusCompraDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Informar data Inicial")
    private String dataInicial;

    @NotEmpty(message = "Informar data Final")
    private String dataFinal;
    private String codigoVenda="";

    private String statusVenda="";
    private String codigoProduto="";
    private String nomeProduto="";
    private String valorVendaProduto="";
    private String quantidadeEstoque="";
    private String codigoCliente="";
    private String nomeCliente="";
    private String cpfCliente="";
    private String emailCliente="";
    private String telefoneCliente="";

}
