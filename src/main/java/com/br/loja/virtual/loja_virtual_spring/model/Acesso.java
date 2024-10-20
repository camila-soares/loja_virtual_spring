package com.br.loja.virtual.loja_virtual_spring.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name = "acesso")
@SequenceGenerator(name = "seq_acesso", sequenceName = "seq_acesso", allocationSize = 1, initialValue = 1)
@Data
public class Acesso implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acesso")
    private Long id;

    /*Acesso referente a roles dos usuários ex: ROLE_ADMIN ou ROLE_SECRETARIO*/
    @Column(nullable = false)
   private String descricao;

    @Override
    public String getAuthority() {
        return descricao;
    }
}
