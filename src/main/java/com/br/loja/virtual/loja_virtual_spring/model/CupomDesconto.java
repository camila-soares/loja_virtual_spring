package com.br.loja.virtual.loja_virtual_spring.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ValueGenerationType;

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

    @Column(nullable = false)
    private String codDesc;

    private BigDecimal valorRealDesconto;

    private BigDecimal valorPorcentDesconto;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataValidadeCupom;
}
