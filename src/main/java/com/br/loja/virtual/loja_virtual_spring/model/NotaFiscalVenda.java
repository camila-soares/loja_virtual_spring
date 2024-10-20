package com.br.loja.virtual.loja_virtual_spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "nota_fiscal_venda")
@SequenceGenerator(name = "seq_nota_fiscal_venda", sequenceName = "seq_nota_fiscal_venda", allocationSize = 1, initialValue = 1)
@Data
public class NotaFiscalVenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_venda")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String serie;

    @Column(nullable = false)
    private String tipo;

    @Column(columnDefinition = "text", nullable = false)
    private String xml;

    @Column(columnDefinition = "text", nullable = false)
    private String pdf;


    @OneToOne
    @JoinColumn(name = "venda_compra_loja_virtual_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "venda_compra_loja_virtual_fk"))
    private VendaCompraLojaVirtual vendaCompraLojaVirtual;

}
