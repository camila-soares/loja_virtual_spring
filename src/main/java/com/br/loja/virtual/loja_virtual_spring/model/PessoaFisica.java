package com.br.loja.virtual.loja_virtual_spring.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Entity
@Table(name = "pessoa_fisica")
@Data
public class PessoaFisica  extends Pessoa {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String cpf;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
}
