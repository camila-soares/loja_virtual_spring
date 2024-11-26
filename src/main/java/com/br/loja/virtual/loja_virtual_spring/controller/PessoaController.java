package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.service.PessoaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;

@Controller
@RestController
public class PessoaController {

    @Autowired
    private PessoaUserService pessoaUserService;

    @PostMapping(value = "/pessoaPj")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptinLojaVirtual {

        PessoaJuridica pessoaJuridica1 = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);

        return ResponseEntity.ok().body(pessoaJuridica1);
    }
}
