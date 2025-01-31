package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.repository.AcessoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AcessoService {

    private final AcessoRepository acessoRepository;

    public Acesso salvar(Acesso acesso) throws Exception {

        List<Acesso> acessos = acessoRepository.buscaAcessoDesc(acesso.getDescricao().toUpperCase());

        if (!acessos.isEmpty()) {
                throw new ExceptinLojaVirtual("Já existe " + acesso.getDescricao() + "Cadastrado!");
        }

        return this.acessoRepository.save(acesso);
    }

    public void delete(Long id) {
        this.acessoRepository.deleteById(id);
    }

    public List<Acesso> listaAcesso() {
        return this.acessoRepository.findAll();
    }

    public Acesso obterAcesso(Long id) throws ExceptinLojaVirtual {
        Acesso acesso = this.acessoRepository.findById(id).orElse(null);

        if (acesso == null) {
            throw new ExceptinLojaVirtual("Não encontrado Acesso com código: " + id);
        }

        return acesso;
    }

    public List<Acesso> buscarPorDescricao(String descricao) {
        return this.acessoRepository.buscaAcessoDesc(descricao);
    }
}
