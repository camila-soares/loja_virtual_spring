package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.dto.ItemVendaDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.RelatorioProdutoAlertaEstoqueRequestDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.VendaCompraLojaVirtualDTO;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.ItemVendaLoja;
import com.br.loja.virtual.loja_virtual_spring.model.VendaCompraLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.service.VendaCompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VendaCompraLojaVirtualController {

    private final VendaCompraService vendaCompraService;

    public VendaCompraLojaVirtualController(VendaCompraService vendaCompraService) {
        this.vendaCompraService = vendaCompraService;
    }


    @PostMapping(value = "/relatorioComprasCanceladas")
    public ResponseEntity<List<RelatorioProdutoAlertaEstoqueRequestDTO>>
    relatorioProCompradoNotaFiscalCompra(@RequestBody RelatorioProdutoAlertaEstoqueRequestDTO alertaEstoqueRequestDTO){

        List<RelatorioProdutoAlertaEstoqueRequestDTO> retorno = new ArrayList<>();
        retorno =  vendaCompraService.relatorioComprasCancelada(alertaEstoqueRequestDTO);
        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }



    @PostMapping(value = "/salvarCompraVenda")
    public ResponseEntity<VendaCompraLojaVirtualDTO> salvarCompraVenda(@RequestBody VendaCompraLojaVirtual vendaCompraLojaVirtual) throws MessagingException, UnsupportedEncodingException {

        VendaCompraLojaVirtual vendaSalva = vendaCompraService.save(vendaCompraLojaVirtual);
        VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
        compraLojaVirtualDTO.setValorTotal(vendaSalva.getValorTotal());
        compraLojaVirtualDTO.setPessoa(vendaSalva.getPessoa());

        compraLojaVirtualDTO.setEntrega(vendaSalva.getEnderecoEntrega());
        compraLojaVirtualDTO.setCobranca(vendaSalva.getEnderecoCobranca());

        compraLojaVirtualDTO.setValorDesc(vendaSalva.getValorDesconto());
        compraLojaVirtualDTO.setValorFrete(vendaSalva.getValorFrete());

        for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendaLojas()){
            ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
            itemVendaDTO.setQuantidade(item.getQuantidade());
            itemVendaDTO.setProduto(item.getProduto());
            compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }



       return new ResponseEntity<>(compraLojaVirtualDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/consultaVendaDinamica/{valor}/{tipoConsulta}")
    public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaDinamica(@PathVariable("valor") String valor,
                                                                            @PathVariable("tipoConsulta") String tipoConsulta) {

        List<VendaCompraLojaVirtual> lojaVirtualList = null;
        if (tipoConsulta.equals("POR_ID_PRODUTO")) {
            lojaVirtualList = vendaCompraService.consultaPorProduto(Long.valueOf(valor));
        } else if (tipoConsulta.equals("POR_NOME_PRODUTO")){
            lojaVirtualList = vendaCompraService.consultaPorNomeProduto(valor.toUpperCase().trim());
        } else if (tipoConsulta.equals("POR_NOME_CLIENTE")){
            lojaVirtualList = vendaCompraService.consulgaPorNomeCliente(valor.toUpperCase().trim());
        } else if (tipoConsulta.equals("POR_ENDERECO_COBRANCA")){
        lojaVirtualList = vendaCompraService.consultaPorEnderecoCobranca(valor.toUpperCase().trim());
        } else if (tipoConsulta.equals("POR_ENDERECO_ENTREGA")){
            lojaVirtualList = vendaCompraService.consultaPorEnderecoEntrega(valor.toUpperCase().trim());
        }

        List<VendaCompraLojaVirtualDTO> lojaVirtualDTOList = new ArrayList<>();
        for (VendaCompraLojaVirtual venda : lojaVirtualList){
            VendaCompraLojaVirtualDTO vendaDTO = new VendaCompraLojaVirtualDTO();
            vendaDTO.setValorTotal(venda.getValorTotal());
            vendaDTO.setPessoa(venda.getPessoa());
            vendaDTO.setEntrega(venda.getEnderecoEntrega());
            vendaDTO.setCobranca(venda.getEnderecoCobranca());
            vendaDTO.setValorDesc(venda.getValorDesconto());
            vendaDTO.setValorFrete(venda.getValorFrete());
            for (ItemVendaLoja item : venda.getItemVendaLojas()){
                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProduto(item.getProduto());
                vendaDTO.getItemVendaLoja().add(itemVendaDTO);

            }
            lojaVirtualDTOList.add(vendaDTO);
        }
        return new ResponseEntity<>(lojaVirtualDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/consultaVendaDinamicaFaixaData/{data1}/{data2}")
    public ResponseEntity<List<VendaCompraLojaVirtual>>
    consultaVendaDinamicaFaixaData(
            @PathVariable("data1") String data1,
            @PathVariable("data2") String data2) throws ParseException {

        List<VendaCompraLojaVirtual> compraLojaVirtual = null;


        compraLojaVirtual = vendaCompraService.consultaVendaFaixaData(data1, data2);


        if (compraLojaVirtual == null) {
            compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
        }

        List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

        for (VendaCompraLojaVirtual vcl : compraLojaVirtual) {

            VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

            compraLojaVirtualDTO.setValorTotal(vcl.getValorTotal());
            compraLojaVirtualDTO.setPessoa(vcl.getPessoa());

            compraLojaVirtualDTO.setEntrega(vcl.getEnderecoEntrega());
            compraLojaVirtualDTO.setCobranca(vcl.getEnderecoCobranca());

            compraLojaVirtualDTO.setValorDesc(vcl.getValorDesconto());
            compraLojaVirtualDTO.setValorFrete(vcl.getValorFrete());
            compraLojaVirtualDTO.setId(vcl.getId());

            for (ItemVendaLoja item : vcl.getItemVendaLojas()) {

                ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
                itemVendaDTO.setQuantidade(item.getQuantidade());
                itemVendaDTO.setProduto(item.getProduto());

                compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
            }

            compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

        }

        return new ResponseEntity<>(compraLojaVirtual, HttpStatus.OK);

    }

    @DeleteMapping(value = "/deleteVendaId/{id}")
    public ResponseEntity<HttpStatus> excluirCompraVenda(@PathVariable("id") Long id){
        vendaCompraService.exclusaoTotalVendaBanco2(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/consultaVendaId/{id}")
    public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable("id") Long id) throws ExceptinLojaVirtual {

        VendaCompraLojaVirtual vendaCompraLojaVirtual = vendaCompraService.consultaVendaId(id);
        VendaCompraLojaVirtualDTO virtualDTO = new VendaCompraLojaVirtualDTO();
        virtualDTO.setId(vendaCompraLojaVirtual.getId());
        virtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        virtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());
        virtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());
        virtualDTO.setEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
        virtualDTO.setCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());
        virtualDTO.setValorDesc(vendaCompraLojaVirtual.getValorDesconto());

        for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendaLojas()){
            ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
            itemVendaDTO.setQuantidade(item.getQuantidade());
            itemVendaDTO.setProduto(item.getProduto());
            virtualDTO.getItemVendaLoja().add(itemVendaDTO);
        }
        return new  ResponseEntity<VendaCompraLojaVirtualDTO>(virtualDTO, HttpStatus.OK);

    }
}
