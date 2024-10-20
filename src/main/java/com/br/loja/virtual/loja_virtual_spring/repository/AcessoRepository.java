package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {
}
