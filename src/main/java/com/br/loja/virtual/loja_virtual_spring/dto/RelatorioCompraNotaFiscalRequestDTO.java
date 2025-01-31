package com.br.loja.virtual.loja_virtual_spring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RelatorioCompraNotaFiscalRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nomeProduto="";
    private String nomeFornecedor="";
    private String dataInicial;
    private String dataFinal;
    private String codigoNota="";
    private String codigoProduto="";
}
