package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.dto.UsuarioDTO;
import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
import com.br.loja.virtual.loja_virtual_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PutMapping(value = "/alterarSenha")
    public ResponseEntity<UsuarioDTO> alteraSenha(@RequestBody UsuarioDTO usuario) {
        Usuario usuarioAlterado = usuarioRepository.findById(usuario.getId()).get();

        String senhaCript = new BCryptPasswordEncoder().encode(usuario.getSenhaNova());
        usuarioAlterado.setSenha(senhaCript);
        usuarioAlterado.setDataAtualSenha(Calendar.getInstance().getTime());
        Usuario usuarioEntity = usuarioRepository.save(usuarioAlterado);

        usuario.setId(usuarioEntity.getId());
        usuario.setSenhaNova(usuario.getSenhaNova());


        return ResponseEntity.ok(usuario);
    }
}
