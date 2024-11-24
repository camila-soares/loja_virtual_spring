package com.br.loja.virtual.loja_virtual_spring.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pessoa_fisica")
@Data
public class PessoaFisica  extends Pessoa {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String cpf;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private Date dataNascimento;

    @Transient
    private String senhaTemp;
}
