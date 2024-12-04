package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    @Query(value = "select c from CategoriaProduto c where c.nomeDesc = ?1")
    CategoriaProduto finByDescricao(String nomeDesc);


    @Query(value = "select  count(1) > 0  from CategoriaProduto c where upper(c.nomeDesc) = ?1")
    public boolean verifydescricaoExist(String nomeDesc);
}
