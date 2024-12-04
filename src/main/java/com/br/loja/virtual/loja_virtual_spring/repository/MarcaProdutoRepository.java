package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.MarcaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {

    @Query("select m from MarcaProduto m where upper(trim( m.nomeDesc)) like  %?1%")
    public List<MarcaProduto> buscarMarcaDesc(String desc);
}
