package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProdutoRepository extends CrudRepository<Produto, Long> {


    @Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1))")
    public boolean existeProduto(String nomeCategoria);


    @Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1)) and empresa_id = ?2")
    public boolean existeProduto(String nomeCategoria, Long idEmpresa);


    @Query(value = "select p from Produto p where upper(p.nome) = ?1")
    List<Produto> findByNome(String nome);

    @Query("select a from Produto a where upper(trim(a.nome)) like %?1% and a.empresa.id = ?2")
    public List<Produto> buscarProdutoNome(String nome, Long idEmpresa);
}
