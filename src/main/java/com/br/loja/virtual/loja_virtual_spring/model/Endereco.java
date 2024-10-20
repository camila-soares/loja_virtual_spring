package com.br.loja.virtual.loja_virtual_spring.model;


import com.br.loja.virtual.loja_virtual_spring.enums.TipoEndereco;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1, initialValue = 1)
@Data
public class Endereco implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_endereco")
    private Long id;
    @Column(nullable = false)
   private String logradouro;
    @Column(nullable = false)
   private String cep;
    @Column(nullable = false)
   private String numero;

   private String complemento;
   @Column(nullable = false)
   private String bairro;
   @Column(nullable = false)
   private String cidade;
   @Column(nullable = false)
   private String uf;

   @ManyToOne(targetEntity = Pessoa.class)
   @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
   private Pessoa pessoa;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private TipoEndereco tipoEndereco;
}
