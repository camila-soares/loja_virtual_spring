package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {

    @Query("select a from Acesso a where upper(trim(a.descricao)) like %?1%")
    List<Acesso> buscaAcessoDesc(String descricao);

//    @Query(value = "select a from Acesso a where a.empresa.id = ?1 ")
//    public List<Acesso> findPorPage(Long idEmpresa, Pageable pageable);
}
