package com.br.loja.virtual.loja_virtual_spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CNPJ;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pessoa_juridica")
@Data
public class PessoaJuridica extends Pessoa {


  @CNPJ(message = "CNPJ Inválido")
  @Column(nullable = false)
  private String cnpj;

  @Column(nullable = false)
  private String inscEstadual;

  private String inscMunicipal;

  @Column(nullable = false)
  private String nomeFantasia;

  @Column(nullable = false)
  private String razaoSocial;

  private String categoria;
}
