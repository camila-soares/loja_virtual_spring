package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.model.FormaPagamento;
import com.br.loja.virtual.loja_virtual_spring.repository.FormaPagamantoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    private final FormaPagamantoRepository formaPagamantoRepository;

    public FormaPagamentoService(FormaPagamantoRepository formaPagamantoRepository) {
        this.formaPagamantoRepository = formaPagamantoRepository;
    }


    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamantoRepository.save(formaPagamento);
    }

    public List<FormaPagamento> listarFormaPagamentos() {
        return formaPagamantoRepository.findAll();
    }

    public List<FormaPagamento> listarFormaPagamentoPorEmpresa(Long idEmpresa) {

        List<FormaPagamento> pagamentoList = formaPagamantoRepository.findFormaPagamentoByEmpresa(idEmpresa);

        return pagamentoList;
    }
}
