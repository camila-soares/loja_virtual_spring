package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaFiscalCompraRepository;
import com.br.loja.virtual.loja_virtual_spring.service.NotaFiscalCompraService;
import org.aspectj.weaver.ast.Not;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Controller
public class NotaFiscalCompreController {

    @Autowired
    private NotaFiscalCompraRepository notaFiscalCompraRepository;

    @Autowired
    private NotaFiscalCompraService notaFiscalCompraService;

    @PostMapping(value = "/cadastrarNotaFicalCompra")
    public ResponseEntity<NotaFiscalCompra> cadastrarNotaFiscalCompra(@Valid @RequestBody NotaFiscalCompra notaFiscalCompra) {

      NotaFiscalCompra notaFiscalComprareturn =  notaFiscalCompraService.cadastrarNotaFiscalCompra(notaFiscalCompra);

      return ResponseEntity.ok(notaFiscalComprareturn);
    }

    @DeleteMapping(value = "/deleteNotaFiscalCompraPorId/{id}")
    public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) {

        notaFiscalCompraService.deletarNotaFiscalCompra(id);

        return new ResponseEntity<>("Nota fiscal de compras removidas", HttpStatus.OK);
    }


    @GetMapping(value = "/buscarNotaFiscalPorId/{id}")
    public ResponseEntity<NotaFiscalCompra> buscarNotaFiscalPorId(@PathVariable("id") Long id) throws ExceptinLojaVirtual {

        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraService.buscarNotaFiscalCompraPorId(id);


        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
    }

}
