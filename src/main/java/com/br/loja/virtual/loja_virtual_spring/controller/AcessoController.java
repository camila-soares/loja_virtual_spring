package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping(value = "/listarAcessos")
    public ResponseEntity<List<Acesso>> listarAcesso() {
        List<Acesso> acessoList = acessoService.listaAcesso();
        return new ResponseEntity<>(acessoList, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/obterAcesso/{id}")
    public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) {
        Acesso acesso = acessoService.obterAcesso(id);
        return new ResponseEntity<>(acesso, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/buscarPorDescricao/{descricao}")
    public ResponseEntity<List<Acesso>> buscarPorDescricao(@PathVariable("descricao") String descricao) {
        List<Acesso> acessos = acessoService.buscarPorDescricao(descricao);
        return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteAcesso/{id}")
    public String delete(@PathVariable Long id) {
        acessoService.delete(id);
        return "Acesso Deletado com sucesso!";
    }
}
