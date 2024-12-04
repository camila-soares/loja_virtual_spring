package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.NotaItemProduto;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {

    @Query(value = "select a from NotaItemProduto a where a.produto.id = ?1 and a.notaFiscalCompra.id =?2")
    List<NotaItemProduto> findNotaItemProdutoByProdutoAndNota(Long idProduto, Long idNotaFiscal);

    @Query(value = "select a from NotaItemProduto a where a.produto.id = ?1")
    List<NotaItemProduto> findNotaItemByProduto(Long idProduto);

    @Query("select a from NotaItemProduto a where a.notaFiscalCompra.id = ?1")
    List<NotaItemProduto> findNotaItemByNotaFiscal(Long idNotaFiscal);

    @Query(value = "select a from NotaItemProduto a where a.empresa.id =?1")
    List<NotaItemProduto> findNotaItemByEmpresa(Long idEmpresa);

}
