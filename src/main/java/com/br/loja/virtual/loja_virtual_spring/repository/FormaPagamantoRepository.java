package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FormaPagamantoRepository  extends JpaRepository<FormaPagamento, Long> {

    @Query(value = "select f FROM FormaPagamento f where f.empresa.id =?1")
    List<FormaPagamento> findFormaPagamentoByEmpresa(Long idEmpresa);
}
