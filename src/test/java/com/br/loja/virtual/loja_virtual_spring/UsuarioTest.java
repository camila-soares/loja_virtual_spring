package com.br.loja.virtual.loja_virtual_spring;

import com.br.loja.virtual.loja_virtual_spring.controller.PessoaController;
import com.br.loja.virtual.loja_virtual_spring.enums.TipoEndereco;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Endereco;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaRepository;
import com.br.loja.virtual.loja_virtual_spring.service.PessoaUserService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("dev")
@SpringBootTest(classes = LojaVirtualSpringApplication.class)
public class UsuarioTest {

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testPessoa() throws ExceptinLojaVirtual, MessagingException, UnsupportedEncodingException {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("Camila Soares Xavier");
        pessoaJuridica.setEmail("camila.silva@actdigital.com");
        pessoaJuridica.setCnpj(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        pessoaJuridica.setTelefone("123456789");
        pessoaJuridica.setInscEstadual("87687687687");
        pessoaJuridica.setInscMunicipal("123456789");
        pessoaJuridica.setRazaoSocial("Camila Soares Xavier LTDA");
        pessoaJuridica.setNomeFantasia("Camila Soares Xavier LTDA");

        Endereco endereco1 = new Endereco();
        endereco1.setLogradouro("Rua do Rua do");
        endereco1.setBairro("Agua fria");
        endereco1.setCidade("Sao Paulo");
        endereco1.setCep("98989898");
        endereco1.setComplemento("ANdar 1");
        endereco1.setNumero("178");
        endereco1.setPessoa(pessoaJuridica);
        endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
        endereco1.setUf("SP");
        endereco1.setEmpresa(pessoaJuridica);

        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua do sol");
        endereco2.setBairro("Arruda");
        endereco2.setCidade("Parana");
        endereco2.setCep("7677676");
        endereco2.setComplemento("andar 5");
        endereco2.setNumero("55");
        endereco2.setUf("PR");
        endereco2.setPessoa(pessoaJuridica);
        endereco2.setTipoEndereco(TipoEndereco.ENTREGA);

        pessoaJuridica.getEnderecos().add(endereco1);
        pessoaJuridica.getEnderecos().add(endereco2);

        endereco2.setEmpresa(pessoaJuridica);


       pessoaJuridica = pessoaController.salvarPessoaJuridica(pessoaJuridica).getBody();

       assertEquals(true, pessoaJuridica.getId() > 0);

       for (Endereco endereco : pessoaJuridica.getEnderecos()) {
           assertEquals(true, endereco.getId() > 0);
       }

       assertEquals(2, pessoaJuridica.getEnderecos().size());

//        PessoaFisica pessoaFisica = new PessoaFisica();
//        pessoaFisica.setNome("Joao");
//        pessoaFisica.setEmail("joao@gmail.com");
//        pessoaFisica.setCpf("123456789");
//        pessoaFisica.setTelefone("123456789");
//
//        pessoaFisica.setEmpresa(pessoaFisica);
    }
}
