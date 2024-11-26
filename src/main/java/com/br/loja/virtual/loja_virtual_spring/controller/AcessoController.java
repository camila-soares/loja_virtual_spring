package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.repository.AcessoRepository;
import com.br.loja.virtual.loja_virtual_spring.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(o)
@RestController
@Controller
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @ResponseBody
    //@Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/salvarAcesso")
    public ResponseEntity<Acesso> salvar(@RequestBody Acesso acesso) throws Exception {

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
    public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptinLojaVirtual {
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
    //@Secured({ "ROLE_ADMIN", "ROLE_GERENTE"})
    @DeleteMapping(value = "/deleteAcesso/{id}")
    public String delete(@PathVariable Long id) {
        acessoService.delete(id);
        return "Acesso Deletado com sucesso!";
    }

//    @ResponseBody
//    @GetMapping(value = "camil/listaPorPageAcesso/{idEmpresa}/{pagina}")
//    public ResponseEntity<List<Acesso>> page(@PathVariable("idEmpresa") Long idEmpresa,
//                                             @PathVariable("pagina") Integer pagina){
//
//        Pageable pageable = PageRequest.of(pagina, 5, Sort.by("descricao"));
//
//        List<Acesso> lista = acessoRepository.findPorPage(idEmpresa, pageable);
//
//        return new ResponseEntity<List<Acesso>>(lista, HttpStatus.OK);
//    }
}
