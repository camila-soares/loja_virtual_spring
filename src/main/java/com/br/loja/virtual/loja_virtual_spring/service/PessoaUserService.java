package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendEmailService sendEmailService;


    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pj) throws ExceptinLojaVirtual, MessagingException, UnsupportedEncodingException {



        PessoaJuridica pessoaJuridicaexist = pessoaRepository.findByCnpj(pj.getCnpj());

        if (pj == null) {
            throw new ExceptinLojaVirtual("Pessoa juridica não pode ser nulo");
        }

        if (pessoaJuridicaexist != null) {
            throw new ExceptinLojaVirtual("CNPJ já cadastrado" + pj.getCnpj());
        }
       // pj = this.pessoaRepository.save(pj);

        for (int i = 0; i< pj.getEnderecos().size(); i++) {
            pj.getEnderecos().get(i).setPessoa(pj);
            pj.getEnderecos().get(i).setEmpresa(pj);
        }
        pj = this.pessoaRepository.save(pj);

        Usuario usuarioPj = usuarioRepository.findByPessoa(pj.getId(), pj.getEmail());

        if (usuarioPj == null) {
            String constraint = usuarioRepository.consultaConstraintAcesso();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
            }
        }
           usuarioPj = new Usuario();
           usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
           usuarioPj.setEmpresa(pj);
           usuarioPj.setPessoa(pj);
           usuarioPj.setLogin(pj.getEmail());

           String senha = "" + Calendar.getInstance().getTimeInMillis();
           String senhaCript = new BCryptPasswordEncoder().encode(senha);

           usuarioPj.setSenha(senhaCript);
           usuarioPj = this.usuarioRepository.save(usuarioPj);

           usuarioRepository.inserAcessoUserPJ(usuarioPj.getId());

           StringBuilder msgHtml = new StringBuilder();
           msgHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
           msgHtml.append("<b>Login: </b>").append(pj.getEmail()).append("<br>");
           msgHtml.append("<b>Senha: </b>").append(senha).append("<br>");
           msgHtml.append("<b>Obrigado! </br>");

           try {
               sendEmailService.enviarEmailHtml("Sua senha foi gerada", msgHtml.toString(), pj.getEmail());
           }catch (Exception e){
               e.printStackTrace();
           }
        return pj;
    }
}
