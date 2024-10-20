package com.br.loja.virtual.loja_virtual_spring;

import com.br.loja.virtual.loja_virtual_spring.controller.AcessoController;
import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
import com.br.loja.virtual.loja_virtual_spring.repository.AcessoRepository;
import com.br.loja.virtual.loja_virtual_spring.service.AcessoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaVirtualSpringApplication.class)
class LojaVirtualSpringApplicationTests {

	@Autowired
	private AcessoController acessoController;

	@Autowired
    private AcessoRepository acessoRepository;


    @Test
	void testCadastraAcesso() {

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");

		acessoController.salvar(acesso);
	}

}
