package com.br.loja.virtual.loja_virtual_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.br.loja.virtual.loja_virtual_spring.model")
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = "com.br.loja.virtual.loja_virtual_spring.repository")
@EnableTransactionManagement
public class LojaVirtualSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualSpringApplication.class, args);
	}

}
