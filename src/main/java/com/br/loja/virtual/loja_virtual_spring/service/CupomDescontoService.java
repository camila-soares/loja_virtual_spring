package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.CupomDesconto;
import com.br.loja.virtual.loja_virtual_spring.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CupomDescontoService {

    private final CupomDescontoRepository cupomDescontoRepository;

    public CupomDescontoService(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    public List<CupomDesconto> listarTodos() {
        return cupomDescontoRepository.findAll();
    }

    public CupomDesconto buscarPorId(Long id) throws ExceptinLojaVirtual {
        CupomDesconto cupoem  =  cupomDescontoRepository.findById(id).get();
        if (cupoem == null) {
            throw new ExceptinLojaVirtual("Nào encontrou um cupom de desconto com o código:  " + id);
        }
        return cupoem;
    }

    public void deletar(Long id) {
        cupomDescontoRepository.deleteById(id);
    }

    public CupomDesconto criar(CupomDesconto desconto) {
        return cupomDescontoRepository.save(desconto);
    }

    public List<CupomDesconto> listarPorEmpresa(Long idEmpres) {

        return cupomDescontoRepository.findCupomDescontoByEmpresa(idEmpres);
    }
}
