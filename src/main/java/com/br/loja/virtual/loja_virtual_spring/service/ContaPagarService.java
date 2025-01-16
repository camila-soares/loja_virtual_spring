package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.ContaPagar;
import com.br.loja.virtual.loja_virtual_spring.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContaPagarService {

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    public ContaPagar salvar(ContaPagar contaPagar) throws ExceptinLojaVirtual {

        if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Empresa deve ser informadda", HttpStatus.NOT_FOUND);
        }

        if (contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Pessoa deve ser informada", HttpStatus.NOT_FOUND);
        }

        if (contaPagar.getPessoaFornecedor() == null || contaPagar.getPessoaFornecedor().getId() <= 0) {
            throw new ExceptinLojaVirtual("Pessoa fornecedor deve ser informada", HttpStatus.NOT_FOUND);
        }

        return contaPagarRepository.save(contaPagar);
    }
}
