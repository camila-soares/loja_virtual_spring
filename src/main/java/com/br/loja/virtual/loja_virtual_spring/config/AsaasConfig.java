package com.br.loja.virtual.loja_virtual_spring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AsaasConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
