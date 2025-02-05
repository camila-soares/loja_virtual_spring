package com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta;

import lombok.Data;

import java.io.Serializable;

@Data
public class ToEnvioEtiquetaDTO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String phone;
    private String email;
    private String document;
    private String company_document;
    private String state_register;
    private String address;
    private String complement;
    private String number;
    private String district;
    private String city;
    private String state_abbr;
    private String country_id;
    private String postal_code;
    private String note;
}
