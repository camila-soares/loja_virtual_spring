package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NotaFiscalVendaaRepository extends JpaRepository<NotaFiscalVenda, Long> {


    @Query(value = "select n from NotaFiscalVenda n where n.vendaCompraLojaVirtual.id = ?1")
    List<NotaFiscalVenda> findNotaFiscalVendaByVendaCompraLojaVirtual(Long idVenda);

    @Query(value = "select n from NotaFiscalVenda n where n.vendaCompraLojaVirtual.id = ?1")
    NotaFiscalVenda findNotaFiscalVendaByVenda(Long idVenda);
}
