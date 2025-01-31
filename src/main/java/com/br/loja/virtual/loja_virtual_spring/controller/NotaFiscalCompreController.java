package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioCompraNotaFiscalRequestDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioCompraNotaFiscalResponseDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioProdutoAlertaEstoqueRequestDTO;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalCompra;
import com.br.loja.virtual.loja_virtual_spring.model.NotaFiscalVenda;
import com.br.loja.virtual.loja_virtual_spring.service.NotaFiscalCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Controller
public class NotaFiscalCompreController {


    private final NotaFiscalCompraService notaFiscalCompraService;

    public NotaFiscalCompreController(NotaFiscalCompraService notaFiscalCompraService) {
        this.notaFiscalCompraService = notaFiscalCompraService;
    }

    @PostMapping(value = "/relatorioProCompradoNotaFiscalCompra")
    public ResponseEntity<List<RelatorioCompraNotaFiscalResponseDTO>> relatorioProCompradoNotaFiscalCompra(@RequestBody
                                                                  RelatorioCompraNotaFiscalRequestDTO fiscalRequestDTO){

        List<RelatorioCompraNotaFiscalResponseDTO> retorno = new ArrayList<>();
        notaFiscalCompraService.relatorioProCompradoNotaFiscalCompra(fiscalRequestDTO);
        retorno =  notaFiscalCompraService.relatorioProCompradoNotaFiscalCompra(fiscalRequestDTO);
        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    @PostMapping(value = "/relatorioAlertaEsqtoque")
    public ResponseEntity<List<RelatorioProdutoAlertaEstoqueRequestDTO>>
    relatorioProCompradoNotaFiscalCompra(@RequestBody RelatorioProdutoAlertaEstoqueRequestDTO alertaEstoqueRequestDTO){

        List<RelatorioProdutoAlertaEstoqueRequestDTO> retorno = new ArrayList<>();
        retorno =  notaFiscalCompraService.relatorioProEstoqueNotaFiscalCompra(alertaEstoqueRequestDTO);
        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    @PostMapping(value = "/cadastrarNotaFicalCompra")
    public ResponseEntity<NotaFiscalCompra> cadastrarNotaFiscalCompra(@Valid @RequestBody NotaFiscalCompra notaFiscalCompra) {

      NotaFiscalCompra notaFiscalComprareturn =  notaFiscalCompraService.cadastrarNotaFiscalCompra(notaFiscalCompra);

      return ResponseEntity.ok(notaFiscalComprareturn);
    }


    @DeleteMapping(value = "/deleteNotaFiscalCompraPorId/{id}")
    public ResponseEntity<?> deleteNotaFiscalCompraPorId(@PathVariable("id") Long id) {

        notaFiscalCompraService.deletarNotaFiscalCompra(id);

        return new ResponseEntity<>("Nota fiscal de compras removidas", HttpStatus.OK);
    }


    @GetMapping(value = "/buscarNotaFiscalPorId/{id}")
    public ResponseEntity<NotaFiscalCompra> buscarNotaFiscalPorId(@PathVariable("id") Long id) throws ExceptinLojaVirtual {

        NotaFiscalCompra notaFiscalCompra = notaFiscalCompraService.buscarNotaFiscalCompraPorId(id);


        return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
    }

    @GetMapping(value = "/consultaNotaFiscalVendaPorIdVenda/{idVenda}")
    public ResponseEntity<List<NotaFiscalVenda>> consultaNotaFiscalVendaPorIdVenda(@PathVariable Long idVenda) throws ExceptinLojaVirtual {
        List<NotaFiscalVenda> list = notaFiscalCompraService.consultarNotaFiscalVendaPorVenda(idVenda);

        return ResponseEntity.ok(list);
    }


    @GetMapping(value = "/consultaNotaFiscalVendaPorIdVendaUnica/{idVenda}")
    public ResponseEntity<NotaFiscalVenda> consultaNotaFiscalVendaPorIdVendaUnica(@PathVariable Long idVenda) throws ExceptinLojaVirtual {
        NotaFiscalVenda list = notaFiscalCompraService.consultarNotaFiscalVendaPorVendaUnica(idVenda);

        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/buscarNotaFiscalPorDescricao/{descricao}")
    public ResponseEntity<List<NotaFiscalCompra>> buscarNotaFiscalPorId(@PathVariable("descricao") String descricao) throws ExceptinLojaVirtual {

        List<NotaFiscalCompra> notaFiscalCompra = notaFiscalCompraService.buscarNotaFiscalCompraPorDescricao(descricao);


        return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompra, HttpStatus.OK);
    }

}
