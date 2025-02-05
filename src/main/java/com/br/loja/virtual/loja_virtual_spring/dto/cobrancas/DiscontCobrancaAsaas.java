package com.br.loja.virtual.loja_virtual_spring.dto.cobrancas;

import lombok.Data;

import java.io.Serializable;

@Data
public class DiscontCobrancaAsaas implements Serializable {

    private static final long serialVersionUID = 1L;

    private float value;
    private float dueDateLimitDays;
}
