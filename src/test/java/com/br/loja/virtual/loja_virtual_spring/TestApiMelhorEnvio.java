package com.br.loja.virtual.loja_virtual_spring;

import com.br.loja.virtual.loja_virtual_spring.controller.VendaCompraLojaVirtualController;
import com.br.loja.virtual.loja_virtual_spring.dto.calculofrete.EmpresaTransporteDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.cobrancas.ObjetoPostCarneJuno;
import com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta.EnvioEtiquetaDTO;
import com.br.loja.virtual.loja_virtual_spring.enums.ApiTokenIntegracao;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.service.ws.JunoBoletoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.br.loja.virtual.loja_virtual_spring.enums.ApiTokenIntegracao.KEY_API_ASSAS;
import static org.junit.Assert.assertEquals;

@ActiveProfiles("dev")
@SpringBootTest(classes = LojaVirtualSpringApplication.class)
public class TestApiMelhorEnvio {

    @Autowired
    private VendaCompraLojaVirtualController vendaCompraLojaVirtualController;

    @Autowired
    private JunoBoletoService junoBoletoService;

    @Test
    public void testcriarChavePixAsaas() throws Exception {

        String chaveAPi = junoBoletoService.criarChavePixAsaas();

        System.out.println("Chave Asass API" + chaveAPi);
    }

    @Test
    public void testbuscaClientePessoaApiAsaas()  throws Exception{

        ObjetoPostCarneJuno dados = new ObjetoPostCarneJuno();
        dados.setEmail("alex.fernando.egidio@gmail.com");
        dados.setPayerName("alex fernando egidio");
        dados.setPayerCpfCnpj("05916564937");
        dados.setPayerPhone("45999795800");

        String  customer_id =junoBoletoService.buscaClientePessoaApiAsaas(dados);

        assertEquals("cus_000109194830", customer_id);
    }
    @Test
    public void testgerarCarneApiAsaas() throws Exception {

        ObjetoPostCarneJuno juno = new ObjetoPostCarneJuno();
        juno.setEmail("camilasoares1507@gmail.com");
        juno.setPayerName("Camila Soares");
        juno.setPayerCpfCnpj("08146639402");
        juno.setPayerPhone("81995346681");
        juno.setIdVenda(32L);

      String retorno =  junoBoletoService.gerarCarneApiAsaas(juno);

      System.out.println(retorno);


    }

    public static void main(String[] args) throws Exception {


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n" +
                "      \"customer\": \"cus_000005219613\",\n" +
                "      \"billingType\": \"BOLETO\",\n" +
                "      \"value\": 100.00,\n" +
                "      \"dueDate\": \"2023-07-21\"\n" +
                "}");
        Request request = new Request.Builder()
                .url("https://sandbox.asaas.com/api/v3/payments")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("User-Agent", "Mozilla")
                .addHeader("Authorization", "Bearer " + "$aact_MzkwODA2MWY2OGM3MWRlMDU2NWM3MzJlNzZmNGZhZGY6OjE3ZGRlOWY2LWU3NWUtNDUyZi1hODJlLTVjMmNmYjQ2ZjZkODo6JGFhY2hfZjFmMTBiY2YtMDVhOC00NzBjLWFlMzYtYjBhNzg2ZGEzODY0" )
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());


//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"order\":{\"reason_id\":\"2\"}}");
//        Request request = new Request.Builder()
//                .url( ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX +"api/v2/me/shipment/cancel")
//                .post(body)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        System.out.println("CANCELAMENTO"+response.body().string());

//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody requestBody = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/agencies")
//                .get()
//                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        System.out.println("AGENCIAS" + response.body().string());

//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
//        RequestBody requestBody = RequestBody.create(mediaType, "{\n" +
//                "    \"from\": {\n" +
//                "        \"postal_code\": \"96020360\"\n" +
//                "    },\n" +
//                "    \"to\": {\n" +
//                "        \"postal_code\": \"01018020\"\n" +
//                "    },\n" +
//                "    \"products\": [\n" +
//                "        {\n" +
//                "            \"id\": \"x\",\n" +
//                "            \"width\": 11,\n" +
//                "            \"height\": 17,\n" +
//                "            \"length\": 11,\n" +
//                "            \"weight\": 0.3,\n" +
//                "            \"insurance_value\": 10.1,\n" +
//                "            \"quantity\": 1\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"options\": {\n" +
//                "        \"receipt\": false,\n" +
//                "        \"own_hand\": false\n" +
//                "    },\n" +
//                "    \"services\": \"1,2,18\"\n" +
//                "}");
//        Request request = new Request.Builder()
//                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/calculate")
//                .method("POST", requestBody)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        JsonNode jsonNode = new ObjectMapper().readTree(response.body().string());
//
//        Iterator<JsonNode> iterator = jsonNode.iterator();
//
//        List<EmpresaTransporteDTO> empresas = new ArrayList<>();
//
//        while (iterator.hasNext()) {
//            JsonNode node = iterator.next();
//
//            EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();
//
//            if (node.get("id") != null) {
//                empresaTransporteDTO.setId(node.get("id").asText());
//            }
//            if (node.get("name") != null) {
//                empresaTransporteDTO.setNome(node.get("name").asText());
//            }
//            if (node.get("price") != null) {
//                empresaTransporteDTO.setValor(node.get("price").asText());
//            }
//            if (node.get("company") != null) {
//                empresaTransporteDTO.setEmpresa(node.get("company").get("name").asText());
//                empresaTransporteDTO.setPicture(node.get("company").get("picture").asText());
//            }
//            if(empresaTransporteDTO.dadosOK()){
//                empresas.add(empresaTransporteDTO);
//            }
//            System.out.println(empresas.toString());
//        }


//
//        String json = "{" +
//                "   \"service\":3," +
//                "   \"agency\":49," +
//                "   \"from\":{" +
//                "      \"name\":\"Nome do remetente\"," +
//                "      \"phone\":\"53984470102\"," +
//                "      \"email\":\"contato@melhorenvio.com.br\"," +
//                "      \"document\":\"16571478358\"," +
//                "      \"company_document\":\"89794131000100\"," +
//                "      \"state_register\":\"123456\"," +
//                "      \"address\":\"Endereço do remetente\"," +
//                "      \"complement\":\"Complemento\"," +
//                "      \"number\":\"1\"," +
//                "      \"district\":\"Bairro\"," +
//                "      \"city\":\"São Paulo\"," +
//                "      \"country_id\":\"BR\"," +
//                "      \"postal_code\":\"01002001\"," +
//                "      \"note\":\"observação\"" +
//                "   }," +
//                "   \"to\":{" +
//                "      \"name\":\"Nome do destinatário\"," +
//                "      \"phone\":\"53984470102\"," +
//                "      \"email\":\"contato@melhorenvio.com.br\"," +
//                "      \"document\":\"25404918047\"," +
//                "      \"company_document\":\"07595604000177\"," +
//                "      \"state_register\":\"123456\"," +
//                "      \"address\":\"Endereço do destinatário\"," +
//                "      \"complement\":\"Complemento\"," +
//                "      \"number\":\"2\"," +
//                "      \"district\":\"Bairro\"," +
//                "      \"city\":\"Porto Alegre\"," +
//                "      \"state_abbr\":\"RS\"," +
//                "      \"country_id\":\"BR\"," +
//                "      \"postal_code\":\"90570020\"," +
//                "      \"note\":\"observação\"" +
//                "   }," +
//                "   \"products\":[" +
//                "      {" +
//                "         \"name\":\"Papel adesivo para etiquetas 1\"," +
//                "         \"quantity\":3," +
//                "         \"unitary_value\":100.00" +
//                "      }," +
//                "      {" +
//                "         \"name\":\"Papel adesivo para etiquetas 2\"," +
//                "         \"quantity\":1," +
//                "         \"unitary_value\":700.00" +
//                "      }" +
//                "   ]," +
//                "   \"volumes\":[" +
//                "      {" +
//                "         \"height\":15," +
//                "         \"width\":20," +
//                "         \"length\":10," +
//                "         \"weight\":3.5" +
//                "      }" +
//                "   ]," +
//                "   \"options\":{" +
//                "      \"insurance_value\":1000.00," +
//                "      \"receipt\":false," +
//                "      \"own_hand\":false," +
//                "      \"reverse\":false," +
//                "      \"non_commercial\":false," +
//                "      \"invoice\":{" +
//                "         \"key\":\"31190307586261000184550010000092481404848162\"" +
//                "      }," +
//                "      \"platform\":\"Nome da Plataforma\"," +
//                "      \"tags\":[" +
//                "         {" +
//                "            \"tag\":\"Identificação do pedido na plataforma, exemplo: 1000007\"," +
//                "            \"url\":\"Link direto para o pedido na plataforma, se possível, caso contrário pode ser passado o valor null\"" +
//                "         }" +
//                "      ]" +
//                "   }" +
//                "}";
//        Gson gson = new Gson();
//        EnvioEtiquetaDTO envioEtiquetaDTO = gson.fromJson(json, EnvioEtiquetaDTO.class);
//        System.out.println("DTO: " +envioEtiquetaDTO);
//                String jsonEnvio = new ObjectMapper().writeValueAsString(json);
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType,json);
//        Request request = new Request.Builder().
//                url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/cart")
//                .method("POST", body).addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
//                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br").build();
//        Response response = client.newCall(request).execute();
//
//        System.out.println("RETORNO " +response.body().string());
//
//    }
    }
}
