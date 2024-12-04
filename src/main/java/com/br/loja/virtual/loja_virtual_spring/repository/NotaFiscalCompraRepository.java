package com.br.loja.virtual.loja_virtual_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface NotaFiscalCompra extends JpaRepository<NotaFiscalCompra, Long> {
    
}
