package com.br.loja.virtual.loja_virtual_spring.repository;

import com.br.loja.virtual.loja_virtual_spring.model.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {


}
