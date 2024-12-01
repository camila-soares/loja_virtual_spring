package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
   PessoaJuridica findByCnpj(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    List<PessoaJuridica> findByCnpjs(String cnpj);


    @Query(value = "select pj from PessoaJuridica pj where pj.inscEstadual = ?1")
    PessoaJuridica existInscricaoEstadual(String inscricaoEstadual);
}
