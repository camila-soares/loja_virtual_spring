package com.br.loja.virtual.loja_virtual_spring.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QsaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String qual;
    private String pai_origem;
    private String nome_resp_legal;
    private String qual_resp_legal;

}
