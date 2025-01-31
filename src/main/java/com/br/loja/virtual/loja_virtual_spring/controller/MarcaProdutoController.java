package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.model.MarcaProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.MarcaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
public class MarcaProdutoController {

    private final MarcaProdutoRepository marcaProdutoRepository;

    public MarcaProdutoController(MarcaProdutoRepository marcaProdutoRepository) {
        this.marcaProdutoRepository = marcaProdutoRepository;
    }

    @PostMapping(value = "/salvarMarcaProduto")
    public ResponseEntity<MarcaProduto> salvarMarcaProduto(@RequestBody MarcaProduto marcaProduto) {
        marcaProdutoRepository.save(marcaProduto);
        return ResponseEntity.ok(marcaProduto);
    }

    @PostMapping(value = "/deleteMarcaProduto")
    public ResponseEntity<Boolean> deleteMarcaProduto(@RequestBody MarcaProduto marcaProduto) {
        marcaProdutoRepository.delete(marcaProduto);
        return ResponseEntity.ok(true);
    }

    @GetMapping(value = "/buscarMarcaProdutoPorId/{id}")
    public ResponseEntity<Optional<MarcaProduto>> listarMarcaProduto(@PathVariable("id") Long id) {

        Optional<MarcaProduto> list = marcaProdutoRepository.findById(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/marcaProduto/{desc}")
    public ResponseEntity<List<MarcaProduto>> listarMarcaProduto(@PathVariable("desc") String desc) {

        List<MarcaProduto> list = marcaProdutoRepository.buscarMarcaDesc(desc);
        return ResponseEntity.ok(list);
    }
}
