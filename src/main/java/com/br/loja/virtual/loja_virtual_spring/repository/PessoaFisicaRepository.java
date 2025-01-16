package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {


    @Query(value = "select pf from  PessoaFisica pf where pf.cpf = ?1")
    PessoaFisica findByCPF(String cpf);

    @Query(value = "select pf from  PessoaFisica pf where pf.nome = ?1")
    List<PessoaFisica> findByNome(String nome);

    @Query(value = "select pf from PessoaFisica pf where pf.email = ?1")
    PessoaFisica findByEmail(String email);
}
