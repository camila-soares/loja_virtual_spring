package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.model.CupomDesconto;
import com.br.loja.virtual.loja_virtual_spring.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CupomDescontoService {

    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;

    public List<CupomDesconto> listarTodos() {
        return cupomDescontoRepository.findAll();
    }

    public CupomDesconto criar(CupomDesconto desconto) {
        return cupomDescontoRepository.save(desconto);
    }

    public List<CupomDesconto> listarPorEmpresa(Long idEmpres) {

        return cupomDescontoRepository.findCupomDescontoByEmpresa(idEmpres);
    }
}
