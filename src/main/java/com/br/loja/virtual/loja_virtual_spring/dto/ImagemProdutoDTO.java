package com.br.loja.virtual.loja_virtual_spring.dto;


import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ImagemProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String imageOriginal;
    private String imageMiniatura;
    private Long produto;
    private Long empresa;
}
