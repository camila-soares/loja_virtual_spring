package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping
    public ResponseEntity<Acesso> salvar(@RequestBody Acesso acesso) {
        return ResponseEntity.ok(acessoService.salvar(acesso));
    }
}
