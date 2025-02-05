package com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductsEnvioEtiquetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String quantity;
    private String unitary_value;
}
