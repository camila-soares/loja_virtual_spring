package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.AvaliacaoProduto;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {


    @Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1")
    List<AvaliacaoProduto> findAvalicaoProdutoByIdProduto(Long idProduto);


    @Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1 and a.pessoa.id = ?2")
    List<AvaliacaoProduto> findAvaliacaoProdutoByIdProdutoAndIdPessoa(Long idProduto, Long idPessoa);

    @Query(value = "select a from AvaliacaoProduto a where a.pessoa.id = ?1")
   List<AvaliacaoProduto> findAvaliacaoProdutoByIdPessoa(Long idPessoa);
}
