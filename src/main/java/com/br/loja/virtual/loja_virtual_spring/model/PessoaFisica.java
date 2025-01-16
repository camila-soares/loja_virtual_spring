package com.br.loja.virtual.loja_virtual_spring.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "pessoa_fisica")
@Data
public class PessoaFisica  extends Pessoa {

    private static final long serialVersionUID = 1L;

    @CPF(message = "CPF não é Válido")
    @Column(nullable = false)
    private String cpf;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private Date dataNascimento;

    @Transient
    private String senhaTemp;
}
