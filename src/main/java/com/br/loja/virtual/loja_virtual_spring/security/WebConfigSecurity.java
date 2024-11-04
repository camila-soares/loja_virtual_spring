package com.br.loja.virtual.loja_virtual_spring.security;

import com.br.loja.virtual.loja_virtual_spring.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity implements HttpSessionListener, WebSecurityCustomizer {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }



    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers(HttpMethod.GET, "/listarAcessos")
                .requestMatchers(HttpMethod.GET, "/obterAcesso/{id}")
                .requestMatchers(HttpMethod.GET, "/buscarPorDescricao/{id}")
                .requestMatchers(HttpMethod.POST, "/salvarAcesso")
                .requestMatchers(HttpMethod.DELETE, "/deleteAcesso/{id}");
    }
}
