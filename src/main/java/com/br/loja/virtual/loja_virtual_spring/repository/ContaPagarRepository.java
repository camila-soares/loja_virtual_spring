package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

    @Query(value = "select c from ContaPagar c where upper(trim(c.descricao)) like %?1%" )
    public List<ContaPagar> buscarContaDesc(String descricao);

    @Query(value = "select c from ContaPagar c where c.pessoa.id = ?1" )
    List<ContaPagar> buscaContaPorPessoa(Long idPessoa);

    @Query(value = "SELECT f from ContaPagar f where f.pessoaFornecedor.id = ?1")
    List<ContaPagar> buscarPorFornecedor(Long idFornecedor);

    @Query(value = "select a from ContaPagar a where a.empresa.id = ?1")
    List<ContaPagar> buscarPorEmpresa(Long idEmpresa);
}

