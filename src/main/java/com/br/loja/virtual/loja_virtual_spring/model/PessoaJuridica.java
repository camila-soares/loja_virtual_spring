package com.br.loja.virtual.loja_virtual_spring.model;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pessoa_juridica")
@Data
public class PessoaJuridica extends Pessoa {


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
