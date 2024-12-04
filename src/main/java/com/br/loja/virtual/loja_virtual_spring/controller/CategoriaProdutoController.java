package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.dto.CategoriaProdutoDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.CategoriaProduto;
import com.br.loja.virtual.loja_virtual_spring.service.CategoriaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @PostMapping(value = "/criarCategoriaProduto")
    public ResponseEntity<CategoriaProdutoDto> criarCategoriaProduto(@RequestBody CategoriaProduto categoriaProduto) throws ExceptinLojaVirtual {

        CategoriaProdutoDto categoriaProdutoDto = categoriaProdutoService.persistir(categoriaProduto);

       return new ResponseEntity<>(categoriaProdutoDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteCategoriaProduto")
    public ResponseEntity<String> deleteCategoriaProduto(@RequestBody CategoriaProduto categoriaProduto) throws ExceptinLojaVirtual {

        categoriaProdutoService.deleteCategoriaProduto(categoriaProduto);


        return new ResponseEntity("Categoria deletada com sucesso", HttpStatus.OK);

    }

    @GetMapping(value = "/buscaCategoriaPorDesc/{desc}")
    public ResponseEntity<CategoriaProduto> buscaCategoriaPorDesc(@PathVariable String desc) throws ExceptinLojaVirtual {

       CategoriaProduto categoriaProduto = categoriaProdutoService.buscaCategoriaPorDesc(desc);


        return new ResponseEntity<>(categoriaProduto, HttpStatus.OK);

    }

    @GetMapping(value = "/listaTodasCategorias")
    public ResponseEntity<Iterable<CategoriaProduto>> listaTodasCategorias() throws ExceptinLojaVirtual {

        Iterable<CategoriaProduto> categoriaProduto = categoriaProdutoService.listaTodasCategorias();


        return new ResponseEntity<>(categoriaProduto, HttpStatus.OK);

    }
}
