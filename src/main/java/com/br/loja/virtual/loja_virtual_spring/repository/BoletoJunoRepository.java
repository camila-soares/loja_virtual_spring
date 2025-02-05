package com.br.loja.virtual.loja_virtual_spring.repository;


import com.br.loja.virtual.loja_virtual_spring.model.BoletoJuno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BoletoJunoRepository extends JpaRepository<BoletoJuno, Long> {
}
