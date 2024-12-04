package com.br.loja.virtual.loja_virtual_spring.controller;

import com.br.loja.virtual.loja_virtual_spring.dto.CEPDto;
import com.br.loja.virtual.loja_virtual_spring.dto.ConsultaCNPJDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.service.PessoaFsicaUserService;
import com.br.loja.virtual.loja_virtual_spring.service.PessoaJuridicaUserService;
import com.br.loja.virtual.loja_virtual_spring.service.ws.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RestController
public class PessoaController {

    @Autowired
    private PessoaFsicaUserService pessoaFsicaUserService;

    @Autowired
    private PessoaJuridicaUserService pessoaJuridicaUserService;

    @Autowired
    private ExternalApiService externalApiService;


    @GetMapping(value = "/consultaCep/{cep}")
    public ResponseEntity<CEPDto> consultaCep(@PathVariable("cep") String cep){

        CEPDto cepDto = externalApiService.consultaCEP(cep);

        return new  ResponseEntity<>(cepDto, HttpStatus.OK);
    }

    @GetMapping(value = "/consultaCnpjReceitaws/{cnpj}")
    public ResponseEntity<ConsultaCNPJDto> consultaCnpj(@PathVariable("cnpj") String cnpj){

        ConsultaCNPJDto consultaCNPJDto = externalApiService.consultaCNPJDto(cnpj);

        return new  ResponseEntity<>(consultaCNPJDto, HttpStatus.OK);
    }

    @GetMapping(value = "/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome){

      List<PessoaFisica> pessoaFisica =  pessoaFsicaUserService.consultaPorNome(nome);
                                return new ResponseEntity<>(pessoaFisica, HttpStatus.OK);
    }

    @GetMapping(value = "/consultaPJCNPJ/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaPJCNPJ(@PathVariable("cnpj") String cnpj){

        List<PessoaJuridica> pessoaJuridicas =  pessoaJuridicaUserService.consultaPorCNPJ(cnpj);
        return new ResponseEntity<>(pessoaJuridicas, HttpStatus.OK);
    }

    @PostMapping(value = "/pessoaPj")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@Valid @RequestBody PessoaJuridica pessoaJuridica) throws ExceptinLojaVirtual, MessagingException, UnsupportedEncodingException {

        PessoaJuridica pessoaJuridica1 = pessoaJuridicaUserService.salvarPessoaJuridica(pessoaJuridica);

        return new  ResponseEntity<>(pessoaJuridica1, HttpStatus.CREATED);
    }

    @PostMapping(value = "/pessoaPf")
    public ResponseEntity<PessoaFisica> salvarPessoaFisica(@Valid @RequestBody PessoaFisica pessoaFisica) throws ExceptinLojaVirtual, MessagingException, UnsupportedEncodingException {

        PessoaFisica pessoaJuridicareturn = pessoaFsicaUserService.salvarPessoaFisics(pessoaFisica);

        return new ResponseEntity<>(pessoaJuridicareturn, HttpStatus.CREATED);
    }
}
