package com.br.loja.virtual.loja_virtual_spring.dto.cobrancas;

import lombok.Data;

import java.io.Serializable;

@Data
public class CobrancaAPIAsaas implements Serializable {
    private static final long serialVersionUID = 1L;

    private String customer;
    private String billingType;
    private String dueDate;
    private float value;
    private String description;
    private String externalReference;
    private float installmentValue;
    private Integer installmentCount;
    private DiscontCobrancaAsaas discount = new DiscontCobrancaAsaas();
    private FineCobrancaAsaas fine = new FineCobrancaAsaas();
    private InterestCobrancaAsaas interest = new InterestCobrancaAsaas();
    private boolean portalService = false;
}
