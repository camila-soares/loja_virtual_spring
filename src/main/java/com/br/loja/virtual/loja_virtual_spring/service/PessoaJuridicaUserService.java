package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.dto.CEPDto;
import com.br.loja.virtual.loja_virtual_spring.dto.ConsultaCNPJDto;
import com.br.loja.virtual.loja_virtual_spring.enums.TipoEndereco;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.*;
import com.br.loja.virtual.loja_virtual_spring.repository.*;
import com.br.loja.virtual.loja_virtual_spring.service.ws.ExternalApiService;
import com.br.loja.virtual.loja_virtual_spring.utils.ValidateCNPJ;
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
public class PessoaJuridicaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private ExternalApiService externalApiService;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;


    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pj) throws ExceptinLojaVirtual, MessagingException, UnsupportedEncodingException {


        validationFields(pj);

      if (pj.getId() == null || pj.getId() <= 0){
          for (int p = 0; p< pj.getEnderecos().size(); p++){
              CEPDto cepDto =   externalApiService.consultaCEP(pj.getEnderecos().get(p).getCep());
              pj.getEnderecos().get(p).setBairro(cepDto.getBairro());
              pj.getEnderecos().get(p).setCidade(cepDto.getLocalidade());
              pj.getEnderecos().get(p).setComplemento(cepDto.getComplemento());
              pj.getEnderecos().get(p).setNumero("156");
              pj.getEnderecos().get(p).setTipoEndereco(TipoEndereco.ENTREGA);
              pj.getEnderecos().get(p).setLogradouro(cepDto.getLogradouro());
              pj.getEnderecos().get(p).setUf(cepDto.getUf());

              pj.getEnderecos().get(p).setPessoa(pj);
              pj.getEnderecos().get(p).setEmpresa(pj);

          }

      } else {
          for (int p = 0; p< pj.getEnderecos().size(); p++){
              Endereco enderecoTemp = enderecoRepository.findById(pj.getEnderecos().get(p).getId()).orElse(null);

              if (!enderecoTemp.getCep().equals(pj.getEnderecos().get(p).getCep())) {
                  CEPDto cepDto =   externalApiService.consultaCEP(pj.getEnderecos().get(p).getCep());

                  pj.getEnderecos().get(p).setBairro(cepDto.getBairro());
                  pj.getEnderecos().get(p).setCidade(cepDto.getLocalidade());
                  pj.getEnderecos().get(p).setComplemento(cepDto.getComplemento());
                  pj.getEnderecos().get(p).setNumero("156");
                  pj.getEnderecos().get(p).setTipoEndereco(TipoEndereco.ENTREGA);
                  pj.getEnderecos().get(p).setLogradouro(cepDto.getLogradouro());
                  pj.getEnderecos().get(p).setUf(cepDto.getUf());


              }
          }
      }


        pj.setEmpresa(pj);
        pj = this.pessoaRepository.save(pj);

        String senha = createUserAndUserAcessPessoaJuridica(pj);

        sendEmailHtmlPessoaJuridica(pj, senha);
        return pj;
    }

    private void validationFields(PessoaJuridica pj) throws ExceptinLojaVirtual {
        if (pj == null) {
            throw new ExceptinLojaVirtual("Pessoa juridica não pode ser nulo");
        }

        if (!ValidateCNPJ.isCNPJ(pj.getCnpj())) {
            throw new ExceptinLojaVirtual("CNPJ Inválido, verifique a numeração corretamente " + pj.getCnpj());
        }

        if (pj.getId() == null && pessoaRepository.findByCnpj(pj.getCnpj()) != null) {
            throw new ExceptinLojaVirtual("CNPJ já cadastrado " + pj.getCnpj());
        }

        if (pj.getId() == null && pessoaRepository.existInscricaoEstadual(pj.getInscEstadual()) != null) {
            throw new ExceptinLojaVirtual("Já existe inscricao estsdual com o número " + pj.getInscEstadual());
        }
    }


    private String createUserAndUserAcessPessoaJuridica(PessoaJuridica pj) {
        Usuario usuarioPj = usuarioRepository.findByPessoa(pj.getId(), pj.getEmail());

        //if (usuarioPj == null) {
        String constraint = usuarioRepository.consultaConstraintAcesso();
        if (constraint != null) {
            jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
        }
        //}
        usuarioPj = new Usuario();
        usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
        usuarioPj.setEmpresa(pj);
        usuarioPj.setPessoa(pj);
        usuarioPj.setLogin(pj.getEmail());

        String senha = "" + Calendar.getInstance().getTimeInMillis();
        String senhaCript = new BCryptPasswordEncoder().encode(senha);

        usuarioPj.setSenha(senhaCript);
        usuarioPj = this.usuarioRepository.save(usuarioPj);

        usuarioRepository.inserAcessoUser(usuarioPj.getId());
      // usuarioRepository.inserAcessoUserPJ(usuarioPj.getId(), "ROLE_ADMIN");
        return senha;
    }

    private void sendEmailHtmlPessoaJuridica(PessoaJuridica pj, String senha) {
        StringBuilder msgHtml = new StringBuilder();
        msgHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
        msgHtml.append("<b>Login: </b>").append(pj.getEmail()).append("<br>");
        msgHtml.append("<b>Senha: </b>").append(senha).append("<br>");
        msgHtml.append("<b>Obrigado! </br>");

        try {
            sendEmailService.enviarEmailHtmlLoginESenha("Sua senha foi gerada", msgHtml.toString(), pj.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<PessoaJuridica> consultaPorCNPJ(String cnpj) {

        List<PessoaJuridica> pessoaJuridicas = pessoaRepository.findByCnpjs(cnpj);
        return pessoaJuridicas;
    }
}
