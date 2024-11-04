package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.repository.AcessoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AcessoService {

    private final AcessoRepository acessoRepository;

    public Acesso salvar(Acesso acesso) {
        return this.acessoRepository.save(acesso);
    }

    public void delete(Long id) {
        this.acessoRepository.deleteById(id);
    }

    public List<Acesso> listaAcesso() {
        return this.acessoRepository.findAll();
    }

    public Acesso obterAcesso(Long id) {
        return this.acessoRepository.findById(id).orElseThrow(() -> new RuntimeException("Acesso não encontrado"));
    }

    public List<Acesso> buscarPorDescricao(String descricao) {
        return this.acessoRepository.buscaAcessoDesc(descricao);
    }
}
