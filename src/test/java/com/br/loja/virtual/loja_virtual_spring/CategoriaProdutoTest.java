package com.br.loja.virtual.loja_virtual_spring;

import com.br.loja.virtual.loja_virtual_spring.controller.CategoriaProdutoController;
import com.br.loja.virtual.loja_virtual_spring.dto.CategoriaProdutoDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.CategoriaProduto;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaFisicaRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

@ActiveProfiles("dev")
@SpringBootTest(classes = LojaVirtualSpringApplication.class)
public class CategoriaProdutoTest {

    @Autowired
    private CategoriaProdutoController categoriaProdutoController;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Test
    public void criarCategoriaProduto() throws ExceptinLojaVirtual {

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findByCnpj("44797404000123");
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findByCPF("08146639402");
//        PessoaJuridica pessoaJuridica = new PessoaJuridica();
//        pessoaJuridica.setNome("Camila Soares Xavier");
//        pessoaJuridica.setEmail("camilasoares1507@gmail.com");
//        pessoaJuridica.setCnpj(String.valueOf(Calendar.getInstance().getTimeInMillis()));
//        pessoaJuridica.setTelefone("123456789");
//        pessoaJuridica.setInscEstadual("87687687687");
//        pessoaJuridica.setInscMunicipal("123456789");
//        pessoaJuridica.setRazaoSocial("Camila Soares Xavier LTDA");
//        pessoaJuridica.setNomeFantasia("Camila Soares Xavier LTDA");

        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setEmpresa(pessoaJuridica.getEmpresa());
        categoriaProduto.setNomeDesc("CALÇADOS");

        categoriaProdutoController.criarCategoriaProduto(categoriaProduto);
    }
}
