package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.dto.CEPDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaFisicaRepository;
import com.br.loja.virtual.loja_virtual_spring.utils.ValidateCPF;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class PessoaFsicaUserService {

    private final ServiceContagemAcessoApi serviceContagemAcessoApi;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final SendEmailService sendEmailService;
    private final UsuarioService usuarioService;

    public PessoaFsicaUserService(ServiceContagemAcessoApi serviceContagemAcessoApi, PessoaFisicaRepository pessoaFisicaRepository, SendEmailService sendEmailService, UsuarioService usuarioService) {
        this.serviceContagemAcessoApi = serviceContagemAcessoApi;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.sendEmailService = sendEmailService;
        this.usuarioService = usuarioService;
    }


    private void sendEmailHtmlPessoaFisica(PessoaFisica pf, String senha) {
        StringBuilder msgHtml = new StringBuilder();
        msgHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
        msgHtml.append("<b>Login: </b>").append(pf.getEmail()).append("<br>");
        msgHtml.append("<b>Senha: </b>").append(senha).append("<br>");
        msgHtml.append("<b>Obrigado! </br>");

        try {
            sendEmailService.enviarEmailHtmlLoginESenha("Sua senha foi gerada", msgHtml.toString(), pf.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public PessoaFisica salvarPessoaFisics(PessoaFisica pf) throws ExceptinLojaVirtual, MessagingException, UnsupportedEncodingException {
        if (pf == null) {
            throw new ExceptinLojaVirtual("Pessoa fisica não pode ser nulo");
        }

        if (!ValidateCPF.isCPF(pf.getCpf())) {
            throw new ExceptinLojaVirtual("CPF Inválido, verifique a numeração corretamente " + pf.getCpf());
        }

        if (pf.getId() == null && pessoaFisicaRepository.findByCPF(pf.getCpf()) != null) {
            throw new ExceptinLojaVirtual("CPF já cadastrado " + pf.getCpf());
        }

        for (int i = 0; i< pf.getEnderecos().size(); i++) {
            pf.getEnderecos().get(i).setPessoa(pf);
            pf.getEnderecos().get(i).setEmpresa(pf);
        }
        pf = this.pessoaFisicaRepository.save(pf);

        String senha = usuarioService.createUsuarioAndUsuarioAcessoByPessoaJuridicaAndPessoaFisica(null, pf);

        sendEmailHtmlPessoaFisica(pf, senha);
        return pf;
    }



    public CEPDto consultaCEP(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+cep+"/json/", CEPDto.class).getBody();

    }

    public List<PessoaFisica> consultaPorNome(String nome) {

        serviceContagemAcessoApi.atualizaAcessoEndpointPF();
        List<PessoaFisica> pessoaFisica = pessoaFisicaRepository.findByNome(nome);
       return pessoaFisica;
    }
}
