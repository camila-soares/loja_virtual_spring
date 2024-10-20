package com.br.loja.virtual.loja_virtual_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pessoa_pj")
@Data
public class PessoaJuridica extends Pessoa {


  private String cnpj;
  private String inscEstadual;
  private String inscMunicial;
  private String nomeFantasia;
  private String razaoSocial;
  private String categoria;
}
