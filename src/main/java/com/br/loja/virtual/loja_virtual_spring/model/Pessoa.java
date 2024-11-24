package com.br.loja.virtual.loja_virtual_spring.model;


import com.br.loja.virtual.loja_virtual_spring.enums.TipoEndereco;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "seq_pessoa", sequenceName = "seq_pessoa", allocationSize = 1, initialValue = 1)
@Data
public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column
    private String tipoPessoa;

    @Column(nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<Endereco>();

//    @ManyToOne(targetEntity = PessoaJuridica.class)
//    @JoinColumn(name = "empresa_id", nullable = true,
//            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
//    private PessoaJuridica empresa;

    public Endereco enderecoEntrega() {

        Endereco enderecoReturn = null;

        for (Endereco endereco : enderecos) {
            if (endereco.getTipoEndereco().name().equals(TipoEndereco.ENTREGA.name())) {
                enderecoReturn = endereco;
                break;
            }
        }

        return enderecoReturn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
