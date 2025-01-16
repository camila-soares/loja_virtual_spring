package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.ImagemProduto;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {


    @Query("select i from ImagemProduto i where i.produto.id = ?1")
    List<ImagemProduto> findImagemProdutoByIdProduto(Long idProduto);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query( value = "delete  from  ImagemProduto i where i.produto.id = ?1")
    void deleteTodaImagensDoProduto(Long idProduto);
}
