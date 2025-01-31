package com.br.loja.virtual.loja_virtual_spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RelatorioCompraNotaFiscalResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String valorVendaProduto;
    private String codigoProduto;
    private String nomeProduto;
    private String quantidadeComprada;
    private String codigoFornecedor;
    private String nomeFornecedor;
    private String dataCompra;
}

