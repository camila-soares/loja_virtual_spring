package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.model.CupomDesconto;
import com.br.loja.virtual.loja_virtual_spring.service.CupomDescontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CupomDescontoController{

    @Autowired
    private CupomDescontoService cupomDescontoService;


    @PostMapping(value = "/criarCupomDesconto")
    public ResponseEntity<CupomDesconto> criarCupomDesconto(@RequestBody CupomDesconto desconto){

        desconto = cupomDescontoService.criar(desconto);
        return new ResponseEntity<>(desconto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/listarCupomDesconto")
    public ResponseEntity<List<CupomDesconto>> listarCupomDesconto(){

        List<CupomDesconto> list = cupomDescontoService.listarTodos();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/listarCupomDescontoPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<CupomDesconto>> listarCupomDesconto(@PathVariable Long idEmpresa){

        List<CupomDesconto> list = cupomDescontoService.listarPorEmpresa(idEmpresa);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
