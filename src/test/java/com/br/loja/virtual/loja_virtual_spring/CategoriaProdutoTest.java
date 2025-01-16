package com.br.loja.virtual.loja_virtual_spring;

import com.br.loja.virtual.loja_virtual_spring.controller.CategoriaProdutoController;
import com.br.loja.virtual.loja_virtual_spring.controller.CupomDescontoController;
import com.br.loja.virtual.loja_virtual_spring.dto.CategoriaProdutoDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.CategoriaProduto;
import com.br.loja.virtual.loja_virtual_spring.model.CupomDesconto;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaFisicaRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@ActiveProfiles("dev")
@SpringBootTest(classes = LojaVirtualSpringApplication.class)
public class CategoriaProdutoTest {

    @Autowired
    private CategoriaProdutoController categoriaProdutoController;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private CupomDescontoController cupomDescontoController;

    @Test
    public void criarCupomDesconto() throws ParseException {

        String dat = "10/01/2025";
        String dat1 = "20/01/2025";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataVal = formatter.parse(dat);
        Date dataVal1 = formatter.parse(dat1);
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.getById(7L);
        CupomDesconto desconto = new CupomDesconto();
        //desconto.setId(2L);
        desconto.setCodDesc("VALE100");
        desconto.setValorRealDesconto(BigDecimal.valueOf(50));
        desconto.setValorPorcentDesconto(BigDecimal.TEN);
        desconto.setDataValidadeCupom(dataVal);
        desconto.setEmpresa(pessoaJuridica);

        PessoaJuridica pessoaJuridica1 = pessoaJuridicaRepository.getById(6L);
        CupomDesconto desconto2 = new CupomDesconto();
        //desconto2.setId(2L);
        desconto2.setCodDesc("VALE50");
        desconto2.setValorRealDesconto(BigDecimal.valueOf(50));
        desconto2.setValorPorcentDesconto(BigDecimal.TEN);
        desconto2.setDataValidadeCupom(dataVal1);
        desconto2.setEmpresa(pessoaJuridica1);

        cupomDescontoController.criarCupomDesconto(desconto);
        cupomDescontoController.criarCupomDesconto(desconto2);
    }

//    @Test
//    public void criarCategoriaProduto() throws ExceptinLojaVirtual {
//
//        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findByCnpj("44797404000123");
//        PessoaFisica pessoaFisica = pessoaFisicaRepository.findByCPF("08146639402");
////        PessoaJuridica pessoaJuridica = new PessoaJuridica();
////        pessoaJuridica.setNome("Camila Soares Xavier");
////        pessoaJuridica.setEmail("camilasoares1507@gmail.com");
////        pessoaJuridica.setCnpj(String.valueOf(Calendar.getInstance().getTimeInMillis()));
////        pessoaJuridica.setTelefone("123456789");
////        pessoaJuridica.setInscEstadual("87687687687");
////        pessoaJuridica.setInscMunicipal("123456789");
////        pessoaJuridica.setRazaoSocial("Camila Soares Xavier LTDA");
////        pessoaJuridica.setNomeFantasia("Camila Soares Xavier LTDA");
//
//        CategoriaProduto categoriaProduto = new CategoriaProduto();
//        categoriaProduto.setEmpresa(pessoaJuridica.getEmpresa());
//        categoriaProduto.setNomeDesc("CALÇADOS");
//
//        categoriaProdutoController.criarCategoriaProduto(categoriaProduto);
//    }
}
