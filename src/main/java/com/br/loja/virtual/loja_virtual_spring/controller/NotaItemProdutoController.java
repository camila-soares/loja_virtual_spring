package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import com.br.loja.virtual.loja_virtual_spring.model.NotaItemProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaItemProdutoRepository;
import com.br.loja.virtual.loja_virtual_spring.service.NotaItemProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Controller
public class NotaItemProdutoController {

    @Autowired
    private NotaItemProdutoService notaItemProdutoService;

    @PostMapping(value = "/salvaNotaItemProduto")
    public ResponseEntity<NotaItemProduto> salvar(@RequestBody @Valid NotaItemProduto notaItemProduto) throws Exception {

        NotaItemProduto notaItemProdutoSalvo = notaItemProdutoService.salvar(notaItemProduto);
        return new ResponseEntity<NotaItemProduto>(notaItemProdutoSalvo, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteNotaItemPorId/{id}")
    public ResponseEntity<?> deleteNotaItemPorId(@PathVariable("id") Long id) {

        notaItemProdutoService.deleteNotaItemPorId(id);

        return new ResponseEntity<>("Nota Item do produto removido", HttpStatus.OK);
    }

    @GetMapping(value = "/buscarNotaItemProdutoPorId/{id}")
    public ResponseEntity<NotaItemProduto> buscarNotaItemProdutoPorId(@PathVariable("id") Long id) throws ExceptinLojaVirtual {

        NotaItemProduto notaItemProduto = notaItemProdutoService.buscarNotaItemProdutoPorId(id);


        return new ResponseEntity<NotaItemProduto>(notaItemProduto, HttpStatus.OK);
    }
}
