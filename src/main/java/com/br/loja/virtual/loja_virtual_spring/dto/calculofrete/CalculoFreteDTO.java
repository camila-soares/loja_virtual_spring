package com.br.loja.virtual.loja_virtual_spring.dto.calculofrete;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CalculoFreteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private FromCalculoFrete from;
   private ToCalculoFrete to;
    private List<ProductsCalculoFrete> products = new ArrayList<>();
    private OptionsCalculoFrete options;
    private String services;
}
