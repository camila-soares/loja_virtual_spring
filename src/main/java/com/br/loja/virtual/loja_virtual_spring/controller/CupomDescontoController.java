package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.CupomDesconto;
import com.br.loja.virtual.loja_virtual_spring.service.CupomDescontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CupomDescontoController{

    private final CupomDescontoService cupomDescontoService;

    public CupomDescontoController(CupomDescontoService cupomDescontoService) {
        this.cupomDescontoService = cupomDescontoService;
    }


    @PostMapping(value = "/criarCupomDesconto")
    public ResponseEntity<CupomDesconto> criarCupomDesconto(@RequestBody @Valid CupomDesconto desconto){

        desconto = cupomDescontoService.criar(desconto);
        return new ResponseEntity<>(desconto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/deleteCupomPorId/{id}")
    public ResponseEntity<?> deleteCupomPorId(@PathVariable("id") Long id){

        cupomDescontoService.deletar(id);

        return new ResponseEntity<>("Cupom Desconto removido",HttpStatus.OK);
    }


    @GetMapping(value = "/obterCupom/{id}")
    public ResponseEntity<CupomDesconto> obterCupomPorId(@PathVariable("id") Long id) throws ExceptinLojaVirtual {

        CupomDesconto cupomDesconto = cupomDescontoService.buscarPorId(id);
        return new ResponseEntity<>(cupomDesconto, HttpStatus.OK);
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
