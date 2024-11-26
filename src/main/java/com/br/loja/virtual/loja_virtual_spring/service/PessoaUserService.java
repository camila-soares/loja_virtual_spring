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

import java.util.Calendar;

@Service
public class PessoaUserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pj) throws ExceptinLojaVirtual {

        PessoaJuridica pessoaJuridicaexist = pessoaRepository.findByCnpj(pj.getCnpj());

        if (pj == null) {
            throw new ExceptinLojaVirtual("Pessoa juridica não pode ser nulo");
        }

        if (pessoaJuridicaexist != null) {
            throw new ExceptinLojaVirtual("CNPJ já cadastrado" + pj.getCnpj());
        }
        pj = this.pessoaRepository.save(pj);

        Usuario usuarioPj = usuarioRepository.findByPessoa(pj.getId(), pj.getEmail());

        if (usuarioPj == null) {
           String constraint = usuarioRepository.consultaConstraintAcesso();
           if (constraint != null) {
               jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint +"; commit;");
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
        }
        return pj;
    }
}
