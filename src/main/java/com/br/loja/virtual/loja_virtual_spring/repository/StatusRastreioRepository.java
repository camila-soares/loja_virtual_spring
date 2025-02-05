package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.StatusRatreio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRastreioRepository extends JpaRepository<StatusRatreio, Long> {

    @Query(value = "select s from StatusRatreio s where s.vendaCompraLojaVirtual.id = ?1")
    public List<StatusRatreio> findStatusRatreioByVenda(Long idVenda);

    @Modifying(flushAutomatically = true)
    @Query(nativeQuery = true, value = "update status_rastreio set status = ?1 where venda_compra_loja_virtual_id = ?2")
    public void salvarurlRatreio(String idStatusRatreio, Long idVenda);


}
