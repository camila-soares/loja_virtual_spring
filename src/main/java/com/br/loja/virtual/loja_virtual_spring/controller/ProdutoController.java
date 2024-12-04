package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import com.br.loja.virtual.loja_virtual_spring.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping(value = "/salvarProduto")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) throws ExceptinLojaVirtual {
       Produto produtoretorno = produtoService.criarProduto(produto);
        return new ResponseEntity<Produto>(produtoretorno, HttpStatus.CREATED);
    }
}
