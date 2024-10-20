package com.br.loja.virtual.loja_virtual_spring.model;


import com.br.loja.virtual.loja_virtual_spring.enums.TipoEndereco;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "endereco")
@SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco", allocationSize = 1, initialValue = 1)
@Data
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_endereco")
    private Long id;
   private String logradouro;
   private String cep;
   private String numero;
   private String complemento;
   private String bairro;
   private String cidade;
   private String uf;

   @ManyToOne(targetEntity = Pessoa.class)
   @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "pessoa_fk"))
   private Pessoa pessoa;

   @Enumerated(EnumType.STRING)
   private TipoEndereco tipoEndereco;
}
