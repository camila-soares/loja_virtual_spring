package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {

    @Query(value = "select n from NotaFiscalCompra n where upper(trim(n.descricaoObs)) like %?1%")
    List<NotaFiscalCompra> findNotaByDescricao(String descricao);



    @Query(value = "select n from NotaFiscalCompra n where n.pessoa.id = ?1")
    List<NotaFiscalCompra> findNotaByPessoa(Long idPessoa);

    @Query(value = "select n from NotaFiscalCompra n where n.contaPagar.id = ?1")
    List<NotaFiscalCompra> findNotaByContaPagar(Long idContaPagar);

    @Query(value = "select n from NotaFiscalCompra n where n.empresa.id = ?1")
    List<NotaFiscalCompra> findNotaByEmpresa(Long idEmpresa);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(nativeQuery = true, value = "delete from nota_item_produto where nota_fiscal_conpra_id = ?1;")
    void deleteItemNotaFiscalCompra(Long idNotaFiscalCompra);





}
