package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalVenda;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaFiscalCompraRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaFiscalVendaaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaFiscalCompraService {

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;

    @Autowired
    private NotaFiscalVendaaRepository notaFiscalVendaaRepository;


    public NotaFiscalCompra cadastrarNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        return notaFiscalCompraRepository.save(notaFiscalCompra);
    }

    public List<NotaFiscalCompra> buscarNotaFiscalCompraPorDescricao(String descricao) {
        return notaFiscalCompraRepository.findNotaByDescricao(descricao);
    }

    public void deletarNotaFiscalCompra(Long id) {

        notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);
        notaFiscalCompraRepository.deleteById(id);
    }

    public NotaFiscalCompra buscarNotaFiscalCompraPorId(Long id) throws ExceptinLojaVirtual {
        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);

        if (notaFiscalCompra == null) {
            throw new ExceptinLojaVirtual("Não encontrou Nota Fisdcl com código: " + id, HttpStatus.NOT_FOUND);
        }
        return notaFiscalCompra;
    }

    public List<NotaFiscalVenda> consultarNotaFiscalVendaPorVenda(Long idVenda) throws ExceptinLojaVirtual {

        List<NotaFiscalVenda> notaFiscalVenda = notaFiscalVendaaRepository.findNotaFiscalVendaByVendaCompraLojaVirtual(idVenda);
        if (notaFiscalVenda.isEmpty()) {
            throw new ExceptinLojaVirtual("Nao enconrou nota fiscal com o codigo de venda" + idVenda, HttpStatus.NOT_FOUND);
        }
        return notaFiscalVenda;
    }


    public NotaFiscalVenda consultarNotaFiscalVendaPorVendaUnica(Long idVenda) throws ExceptinLojaVirtual {

        NotaFiscalVenda notaFiscalVenda = notaFiscalVendaaRepository.findNotaFiscalVendaByVenda(idVenda);
        if (notaFiscalVenda == null) {
            throw new ExceptinLojaVirtual("Nao enconrou nota fiscal com o codigo de venda" + idVenda, HttpStatus.NOT_FOUND);
        }
        return notaFiscalVenda;
    }
}
