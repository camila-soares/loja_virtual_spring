package com.br.loja.virtual.loja_virtual_spring.exceptions;

import lombok.Data;

import java.io.Serializable;

@Data
public class ObjetoErroDTO implements Serializable {

    private String erro;
    private String codigo;
}
