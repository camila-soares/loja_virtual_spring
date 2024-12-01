package com.br.loja.virtual.loja_virtual_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoApi {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void atualizaAcessoEndpointPF(){
        jdbcTemplate.execute("begin; update tabela_acesso_end_point set " +
                        "qtd_acesso_end_point = qtd_acesso_end_point + 1;commit;");
    }
}
