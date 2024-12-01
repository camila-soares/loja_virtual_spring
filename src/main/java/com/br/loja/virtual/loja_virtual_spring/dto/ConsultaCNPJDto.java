package com.br.loja.virtual.loja_virtual_spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ConsultaCNPJDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AtividadeCNPJDto> atividade_principal = new ArrayList<>();

    private String data_situacao;
    private String tipo;
    private String nome;
    private String uf;
    private String telefone;
    private String email;

    private List<AtividadeCNPJDto> atividade_secundaria = new ArrayList<>();

    private List<QsaDto> qsas = new ArrayList<>();

    private String situacao;
    private String bairro;
    private String logradouro;
    private String numero;
    private String cep;
    private String municipio;
    private String porte;
    private String abertura;
    private String natureza_juridica;
    private String fantasia;
    private String cnpj;
    private String ultima_atualizacao;
    private String status;
    private String complemento;
    private String motivo_situacao;
    private String situacao_especial;
    private String data_atualizacao_especial;
    private String capital_social;

    @JsonIgnore
    private ExtraDto extra;

    private BillingDto billing;
}
