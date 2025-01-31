package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.ContaPagar;
import com.br.loja.virtual.loja_virtual_spring.repository.ContaPagarRepository;
import com.br.loja.virtual.loja_virtual_spring.service.ContaPagarService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
public class ContaPagarController {

    private final ContaPagarService contaPagarService;

    private final ContaPagarRepository contaPagarRepository;

    public ContaPagarController(ContaPagarService contaPagarService, ContaPagarRepository contaPagarRepository) {
        this.contaPagarService = contaPagarService;
        this.contaPagarRepository = contaPagarRepository;
    }


    @PostMapping(value = "/salvarContaPagar")
    public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody ContaPagar contaPagar) throws ExceptinLojaVirtual {

       ContaPagar contaPagar1 = contaPagarService.salvar(contaPagar);

        return ResponseEntity.ok(contaPagar1);
    }

    @PostMapping(value = "/deleteContaPagar")
    public ResponseEntity<String> deleteContePagar(@RequestBody ContaPagar contaPagar) {

        contaPagarRepository.delete(contaPagar);

        return ResponseEntity.ok().body("conta deletado com sucesso");
    }

    @DeleteMapping(value = "/deleteContaPagarPorId/{id}")
    public void deleteContaPagarPorId(@PathVariable Long id) {

        contaPagarRepository.deleteById(id);

    }

    @GetMapping(value = "/buscaContaPagarPorId/{id}")
    public ContaPagar buscaContaPagarPorId(@PathVariable Long id) {

      Optional<ContaPagar> contaPagar =  contaPagarRepository.findById(id);

      return contaPagar.get();
    }

    @GetMapping(value = "/buscaContaPagarPorDesc/{desc}")
    public  ResponseEntity<List<ContaPagar>> buscaContaPagarPorDescricao(@PathVariable String desc) {

        List<ContaPagar> list = contaPagarRepository.buscarContaDesc(desc);

       return ResponseEntity.ok(list);
    }
}
