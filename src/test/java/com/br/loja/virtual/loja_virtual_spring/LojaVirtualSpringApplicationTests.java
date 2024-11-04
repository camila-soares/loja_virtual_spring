//package com.br.loja.virtual.loja_virtual_spring;
//
//import com.br.loja.virtual.loja_virtual_spring.controller.AcessoController;
//import com.br.loja.virtual.loja_virtual_spring.model.Acesso;
//import com.br.loja.virtual.loja_virtual_spring.repository.AcessoRepository;
//import com.br.loja.virtual.loja_virtual_spring.service.AcessoService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import junit.framework.TestCase;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.apache.catalina.core.ApplicationContext;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = LojaVirtualSpringApplication.class)
//public class LojaVirtualSpringApplicationTests {
//
//	@Autowired
//	private AcessoRepository repository;
//
//	@Autowired
//	private AcessoController controller;
//
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//
//
//	@Test
//     void testListarAcessos() {
//		ResponseEntity<List<Acesso>> acessos = null;
//
//		acessos = controller.listarAcesso();
//		assertNotNull(acessos);
//
//	}
//    @Test
//	void testCadastraAcesso() {
//
//		Acesso acesso = new Acesso();
//		acesso.setDescricao("ROLE_ADMIN");
//
//		acesso = controller.salvar(acesso).getBody();
//
//        assertNotNull(acesso.getId());
//        assertTrue(acesso.getId() > 0);
//
//		assertEquals("ROLE_ADMIN", acesso.getDescricao());
//
//		Acesso acesso2 = repository.findById(acesso.getId()).get();
//
//		assertEquals(acesso.getId(), acesso2.getId());
//
//		repository.deleteById(acesso2.getId());
//		repository.flush();
//		Acesso acesso3 = repository.findById(acesso2.getId()).orElse(null);
//
//		assertNull(acesso3);
//
//		acesso = new Acesso();
//		acesso.setDescricao("ROLE_ALUNO");
//
//		acesso = controller.salvar(acesso).getBody();
//
//		List<Acesso> acessos = repository.buscaAcessoDesc("ALUNO".trim().toUpperCase());
//
//		assertEquals(1, acessos.size());
//
//		repository.deleteById(acesso.getId());
//
//
//	}
//
//	@Test
//	void testDeleteAcesso() {
//
//		Acesso acesso = new Acesso();
//		acesso.setId(10L);
//
//		controller.delete(acesso.getId());
//	}
//
//	/* TESTES DE ENDPOINTS COM MOCKS*/
//
//	@Test
//	public void testRestApiDeleteAcesso() throws Exception {
//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
//
//		MockMvc mockMvc = builder.build();
//
//		Acesso acesso = new Acesso();
//		acesso.setDescricao("ROLE_DELETE");
//
//		acesso = repository.save(acesso);
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		ResultActions retornoApi = mockMvc
//				.perform(MockMvcRequestBuilders.delete("/deleteAcesso/"+acesso.getId())
//						.content(objectMapper.writeValueAsBytes(acesso))
//						.accept(MediaType.APPLICATION_JSON)
//						.contentType(MediaType.APPLICATION_JSON));
//
//		System.out.println("RETORNO API "+retornoApi.andReturn().getResponse().getContentAsString());
//		System.out.println("RETORNO STATUS "+retornoApi.andReturn().getResponse().getStatus());
//
//		assertEquals("Acesso Deletado com sucesso!", retornoApi.andReturn().getResponse().getContentAsString());
//		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
//
//	}
//
//	@Test
//	public void testRestApiCadastroAcesso() throws Exception {
//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
//
//		MockMvc mockMvc = builder.build();
//
//		Acesso acesso = new Acesso();
//		acesso.setDescricao("ROLE_PAUNOCU");
//
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		ResultActions retornoApi = mockMvc
//				.perform(MockMvcRequestBuilders.post("/salvarAcesso")
//						.content(objectMapper.writeValueAsBytes(acesso))
//						.accept(MediaType.APPLICATION_JSON)
//						.contentType(MediaType.APPLICATION_JSON));
//
//	 System.out.println("RETORNO API"+retornoApi.andReturn().getResponse().getContentAsString());
//		//COnverte o retorno da api
//	 Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
//
//	 assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
//	}
//
//	@Test
//	public void testRestApiListarTodosAcessos() throws Exception {
//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
//
//		MockMvc mockMvc = builder.build();
//
//		ArrayList<Acesso> acesso = new ArrayList<Acesso>();
//
//
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		ResultActions retornoApi = mockMvc
//				.perform(MockMvcRequestBuilders.get("/listarAcessos")
//						.content(objectMapper.writeValueAsBytes(acesso))
//						.accept(MediaType.APPLICATION_JSON)
//						.contentType(MediaType.APPLICATION_JSON));
//
//		System.out.println("RETORNO API"+retornoApi.andReturn().getResponse().getContentAsString());
//		//COnverte o retorno da api
//		ArrayList objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), ArrayList.class);
//
//		assertNotNull(objetoRetorno);
//		//assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
//	}
//
//	@Test
//	public void testRestApiObterAcessoPeloId() throws Exception {
//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
//
//		MockMvc mockMvc = builder.build();
//
//		Acesso acesso = new Acesso();
//		acesso.setDescricao("ROLE_OBTER_ACESSO_ID");
//
//		acesso = repository.save(acesso);
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		ResultActions retornoApi = mockMvc
//				.perform(MockMvcRequestBuilders.get("/obterAcesso/"+ acesso.getId())
//						.content(objectMapper.writeValueAsBytes(acesso))
//						.accept(MediaType.APPLICATION_JSON)
//						.contentType(MediaType.APPLICATION_JSON));
//
//		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
//		Acesso objetoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
//		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
//	}
//
//	@Test
//	public void testRestApibuscarPorDescricao() throws Exception {
//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
//
//		MockMvc mockMvc = builder.build();
//
//		Acesso acesso = new Acesso();
//		acesso.setDescricao("ROLE_DELETE");
//
//		acesso = repository.save(acesso);
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		ResultActions retornoApi = mockMvc
//				.perform(MockMvcRequestBuilders.get("/buscarPorDescricao/"+acesso.getDescricao())
//						.content(objectMapper.writeValueAsBytes(acesso))
//						.accept(MediaType.APPLICATION_JSON)
//						.contentType(MediaType.APPLICATION_JSON));
//
//		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
//
//		List<Acesso> retoroApiList = objectMapper.readValue(retornoApi.andReturn()
//				.getResponse().getContentAsString(), new
//				TypeReference<List<Acesso>>() {});
//
//		assertEquals(1, retoroApiList.size());
//		assertEquals(acesso.getDescricao(), retoroApiList.get(0).getDescricao());
//
//		repository.deleteById(acesso.getId());
//
//	}
//}
