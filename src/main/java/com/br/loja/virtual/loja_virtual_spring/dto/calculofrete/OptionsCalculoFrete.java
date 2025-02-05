package com.br.loja.virtual.loja_virtual_spring.dto.calculofrete;

import lombok.Data;

import java.io.Serializable;

@Data
public class OptionsCalculoFrete implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean receipt = false;
    private Boolean own_hand = false;
}
