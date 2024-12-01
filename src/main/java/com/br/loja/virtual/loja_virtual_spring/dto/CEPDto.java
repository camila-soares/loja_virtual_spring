package com.br.loja.virtual.loja_virtual_spring.dto;

import lombok.Data;

@Data
public class CEPDto {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

}
