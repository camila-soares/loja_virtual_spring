package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.model.StatusRatreio;
import com.br.loja.virtual.loja_virtual_spring.service.StatusRatreioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatusRatreioController {

    @Autowired
    private StatusRatreioService statusRatreioService;


    @GetMapping(value = "/consultaStatusRastreioPorVenda/{idVenda}")
    public ResponseEntity<List<StatusRatreio>> consultaStatusRatreioPorVenda(@PathVariable Long idVenda) {

        List<StatusRatreio> list = statusRatreioService.consultarStatusRatreioPorVenda(idVenda);

        return ResponseEntity.ok().body(list);

    }
}
