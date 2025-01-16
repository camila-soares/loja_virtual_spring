package com.br.loja.virtual.loja_virtual_spring.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class AvaliacaoProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private Integer nota;
    private Long pessoa;
    private Long empresa;
    private Long produto;



}
