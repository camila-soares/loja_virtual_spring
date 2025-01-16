package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.model.FormaPagamento;
import com.br.loja.virtual.loja_virtual_spring.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @PostMapping(value = "/salvarFormaDePagamento")
    public ResponseEntity<FormaPagamento> salvarFormaDePagamento(@RequestBody FormaPagamento formaPagamento) {
        formaPagamento = formaPagamentoService.salvar(formaPagamento);

        return new ResponseEntity<>(formaPagamento, HttpStatus.CREATED);
    }

    @GetMapping(value = "listaFormaDePagamentos/{idEmpresa}")
    public ResponseEntity<List<FormaPagamento>> listaFormaDePagamentosPorEmpresa(@PathVariable Long idEmpresa) {
        List<FormaPagamento> formaPagamentoList = formaPagamentoService.listarFormaPagamentoPorEmpresa(idEmpresa);

        return new ResponseEntity<>(formaPagamentoList, HttpStatus.OK);
    }

    @GetMapping(value = "/listaFormaDePagamentos")
    public ResponseEntity<List<FormaPagamento>> listaFormaDePagamentos() {

        return new ResponseEntity<>(formaPagamentoService.listarFormaPagamentos(), HttpStatus.OK);
    }


}
