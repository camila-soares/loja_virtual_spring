package com.br.loja.virtual.loja_virtual_spring.dto.calculofrete;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductsCalculoFrete implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String width;
    private String height;
    private String length;
    private String weight;
    private String insurance_value;
    private String quantity;

}
