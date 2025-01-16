package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaFisica;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
import com.br.loja.virtual.loja_virtual_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createUsuarioAndUsuarioAcessoByPessoaJuridicaAndPessoaFisica(PessoaJuridica pj, PessoaFisica pf) throws ExceptinLojaVirtual {

        String constraint = usuarioRepository.consultaConstraintAcesso();
        if (constraint != null) {
            jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
        }
        Usuario usuario = new Usuario();
        usuario.setDataAtualSenha(Calendar.getInstance().getTime());
        String senha = "" + Calendar.getInstance().getTimeInMillis();
        String senhaCript = new BCryptPasswordEncoder().encode(senha);
        if (pf != null && pj == null) {

            usuario.setPessoa(pf);
            usuario.setEmpresa(pf.getEmpresa());
            usuario.setPessoa(pf);
            usuario.setLogin(pf.getEmail());
            usuario.setSenha(senhaCript);

            Usuario usuariopf = usuario;
            usuario = this.usuarioRepository.save(usuariopf);

            usuarioRepository.inserAcessoUser(usuario.getId());

            return senha;
        } else {

            usuario.setPessoa(pj);
            usuario.setEmpresa(pj.getEmpresa());
            usuario.setLogin(pj.getEmail());
            usuario.setSenha(senhaCript);

            Usuario usuariopj = usuario;
            usuario = this.usuarioRepository.save(usuariopj);
            usuarioRepository.inserAcessoUser(usuario.getId());
            return senha;
        }
    }

}
