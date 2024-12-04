package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaFiscalCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NotaFiscalCompraService {

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;


    public NotaFiscalCompra cadastrarNotaFiscalCompra(NotaFiscalCompra notaFiscalCompra) {
        return notaFiscalCompraRepository.save(notaFiscalCompra);
    }

    public ResponseEntity<?> deletarNotaFiscalCompra(Long id) {

        notaFiscalCompraRepository.deleteItemNotaFiscalCompra(id);
        notaFiscalCompraRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public NotaFiscalCompra buscarNotaFiscalCompraPorId(Long id) throws ExceptinLojaVirtual {
        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id).orElse(null);

        if (notaFiscalCompra == null) {
            throw new ExceptinLojaVirtual("Não encontrou Nota Fisdcl com código: " + id);
        }
        return notaFiscalCompra;
    }
}
