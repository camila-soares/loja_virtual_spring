package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.enums.TipoPessoa;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.ContaPagar;
import com.br.loja.virtual.loja_virtual_spring.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaPagarService {

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    public ContaPagar salvar(ContaPagar contaPagar) throws ExceptinLojaVirtual {

        if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Empresa deve ser informadda");
        }

        if (contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Pessoa deve ser informada");
        }

        if (contaPagar.getPessoaFornecedor() == null || contaPagar.getPessoaFornecedor().getId() <= 0) {
            throw new ExceptinLojaVirtual("Pessoa fornecedor deve ser informada");
        }

        return contaPagarRepository.save(contaPagar);
    }
}
