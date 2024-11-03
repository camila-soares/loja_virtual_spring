package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @ResponseBody
    @PostMapping(value = "/salvarAcesso")
    public ResponseEntity<Acesso> salvar(@RequestBody Acesso acesso) {
        Acesso acessoSalvo = acessoService.salvar(acesso);
        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.CREATED);
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteAcesso/{id}")
    public void delete(@PathVariable Long id) {
        acessoService.delete(id);
    }
}
