package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.dto.CEPDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaFisicaRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.UsuarioRepository;
import com.br.loja.virtual.loja_virtual_spring.utils.ValidateCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

@Service
public class PessoaFsicaUserService {

    @Autowired
    private ServiceContagemAcessoApi serviceContagemAcessoApi;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private JdbcTemplate jdbcTemplate;





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

        String senha = createUserAndUserAcessPessoaFisica(pf);

        sendEmailHtmlPessoaFisica(pf, senha);
        return pf;
    }


    private String createUserAndUserAcessPessoaFisica(PessoaFisica pf) {
        Usuario usuarioPf = usuarioRepository.findByPessoa(pf.getId(), pf.getEmail());

        //if (usuarioPj == null) {
        String constraint = usuarioRepository.consultaConstraintAcesso();
        if (constraint != null) {
            jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
        }
        //}
        usuarioPf = new Usuario();
        usuarioPf.setDataAtualSenha(Calendar.getInstance().getTime());
        usuarioPf.setEmpresa(pf);
        usuarioPf.setPessoa(pf);
        usuarioPf.setLogin(pf.getEmail());

        String senha = "" + Calendar.getInstance().getTimeInMillis();
        String senhaCript = new BCryptPasswordEncoder().encode(senha);

        usuarioPf.setSenha(senhaCript);
        usuarioPf = this.usuarioRepository.save(usuarioPf);

        usuarioRepository.inserAcessoUser(usuarioPf.getId());
        // usuarioRepository.inserAcessoUserPJ(usuarioPj.getId(), "ROLE_ADMIN");
        return senha;
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
