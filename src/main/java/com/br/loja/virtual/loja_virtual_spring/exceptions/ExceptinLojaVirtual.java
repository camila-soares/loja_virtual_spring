package com.br.loja.virtual.loja_virtual_spring.exceptions;

import org.springframework.http.HttpStatus;

public class ExceptinLojaVirtual extends Exception {

    private static final long serialVersionUID = 1L;

    public ExceptinLojaVirtual(String msgErro, HttpStatus notFound) {
        super(msgErro);
    }


}
