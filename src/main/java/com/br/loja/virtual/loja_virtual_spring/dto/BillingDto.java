package com.br.loja.virtual.loja_virtual_spring.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BillingDto implements Serializable {
    private static final long serialVersionUID = 1L;


    private boolean free;
    private boolean database;
}
