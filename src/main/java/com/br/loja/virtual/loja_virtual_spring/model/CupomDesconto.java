package com.br.loja.virtual.loja_virtual_spring.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cupom_desconto")
@SequenceGenerator(name = "seq_cupom_desconto", sequenceName = "seq_cupom_desconto", allocationSize = 1, initialValue = 1)
@Data
public class CupomDesconto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cupom_desconto")
    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty(message = "Informe o código do desconto")
    @Column(nullable = false)
    private String codDesc;


    private BigDecimal valorRealDesconto;

    private BigDecimal valorPorcentDesconto;

    @NotEmpty(message = "Informe a data de validade do cupom")
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValidadeCupom;

    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(name = "empresa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private PessoaJuridica empresa;
}
