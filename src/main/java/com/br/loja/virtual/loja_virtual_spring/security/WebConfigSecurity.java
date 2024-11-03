package com.br.loja.virtual.loja_virtual_spring.security;

import jakarta.servlet.http.HttpSessionListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity implements HttpSessionListener, WebSecurityCustomizer {


    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers(HttpMethod.GET, "/salvarAcesso")
                .requestMatchers(HttpMethod.POST, "/salvarAcesso")
                .requestMatchers(HttpMethod.DELETE, "/deleteAcesso/{id}");
    }
}
