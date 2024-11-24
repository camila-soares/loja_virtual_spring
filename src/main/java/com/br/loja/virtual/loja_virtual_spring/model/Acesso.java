package com.br.loja.virtual.loja_virtual_spring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "acesso")
@SequenceGenerator(name = "seq_acesso", sequenceName = "seq_acesso", allocationSize = 1, initialValue = 1)
@Data
public class Acesso implements GrantedAuthority {


    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acesso")
    private Long id;

    /*Acesso referente a roles dos usuários ex: ROLE_ADMIN ou ROLE_SECRETARIO*/
    @Column(nullable = false)
   private String descricao;

//    @ManyToOne(targetEntity = Pessoa.class)
//    @JoinColumn(name = "empresa_id", nullable = false,
//            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
//    private PessoaJuridica empresa = new PessoaJuridica();


    @JsonIgnore
    @Override
    public String getAuthority() {
        return descricao;
    }
}
