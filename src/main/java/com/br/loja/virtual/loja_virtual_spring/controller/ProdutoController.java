package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import com.br.loja.virtual.loja_virtual_spring.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Controller
@RestController
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping(value = "/salvarProduto")
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) throws ExceptinLojaVirtual, IOException {
        produtoService.criarProduto(produto);
        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/buscarProdutoPorId/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) throws ExceptinLojaVirtual, IOException {

        Produto produto  = produtoService.buscarProdutoPorId(id);

        return new ResponseEntity<>(produto, HttpStatus.OK);
    }
}
