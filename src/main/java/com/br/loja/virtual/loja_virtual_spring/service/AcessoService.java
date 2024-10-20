package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.repository.AcessoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AcessoService {

    private final AcessoRepository acessoRepository;

    public Acesso salvar(Acesso acesso) {
        return this.acessoRepository.save(acesso);
    }

}
