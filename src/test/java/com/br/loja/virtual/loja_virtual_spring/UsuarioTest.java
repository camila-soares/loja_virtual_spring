package com.br.loja.virtual.loja_virtual_spring;

import com.br.loja.virtual.loja_virtual_spring.controller.PessoaController;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaRepository;
import com.br.loja.virtual.loja_virtual_spring.service.PessoaUserService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

@ActiveProfiles("dev")
@SpringBootTest(classes = LojaVirtualSpringApplication.class)
public class UsuarioTest {

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testPessoa() throws ExceptinLojaVirtual {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("Joao");
        pessoaJuridica.setEmail("camila@hotmail.com");
        pessoaJuridica.setCnpj(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        pessoaJuridica.setTelefone("123456789");
        pessoaJuridica.setInscEstadual("87687687687");
        pessoaJuridica.setInscMunicipal("123456789");
        pessoaJuridica.setRazaoSocial("BBBBBBBBB");
        pessoaJuridica.setNomeFantasia("BBBBBBBB");

        pessoaController.salvarPessoaJuridica(pessoaJuridica);

//        PessoaFisica pessoaFisica = new PessoaFisica();
//        pessoaFisica.setNome("Joao");
//        pessoaFisica.setEmail("joao@gmail.com");
//        pessoaFisica.setCpf("123456789");
//        pessoaFisica.setTelefone("123456789");
//
//        pessoaFisica.setEmpresa(pessoaFisica);
    }
}
