package com.br.loja.virtual.loja_virtual_spring.service.ws;

import com.br.loja.virtual.loja_virtual_spring.dto.AsaasApiPagamentoStatus;
import com.br.loja.virtual.loja_virtual_spring.dto.cobrancas.*;
import com.br.loja.virtual.loja_virtual_spring.enums.ApiTokenIntegracao;
import com.br.loja.virtual.loja_virtual_spring.model.AccessTokenJuno;
import com.br.loja.virtual.loja_virtual_spring.model.BoletoJuno;
import com.br.loja.virtual.loja_virtual_spring.model.VendaCompraLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.repository.AccessTokenJunoRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.BoletoJunoRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.VendaCompraLojaVirtualRepository;
import com.br.loja.virtual.loja_virtual_spring.utils.ValidateCPF;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JunoBoletoService  {

    private final VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;
    private final BoletoJunoRepository boletoJunoRepository;

    public JunoBoletoService(AccessTokenJunoService accessTokenJunoService, AccessTokenJunoRepository accessTokenJunoRepository, VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository, BoletoJunoRepository boletoJunoRepository) {
        this.vendaCompraLojaVirtualRepository = vendaCompraLojaVirtualRepository;
        this.boletoJunoRepository = boletoJunoRepository;
    }


    public ObjetoQrCodePixAsaas buscarQrCodeCodigoPix(String idCobranca) throws Exception {

        Client client = new HostIgnoreClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringCliente();
        WebResource webResource = client.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "payments/"+idCobranca +"/pixQrCode");

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token",ApiTokenIntegracao.KEY_API_ASSAS)
                .get(ClientResponse.class);

        String stringRetorno = clientResponse.getEntity(String.class);
        clientResponse.close();

        ObjetoQrCodePixAsaas codePixAsaas = new ObjetoQrCodePixAsaas();

        LinkedHashMap<String, Object> parser = new JSONParser(stringRetorno).parseObject();
        codePixAsaas.setEncodedImage(parser.get("encodedImage").toString());
        codePixAsaas.setPayload(parser.get("payload").toString());

        return codePixAsaas;
    }

    public String gerarCarneApiAsaas (ObjetoPostCarneJuno objetoPostCarneJuno) throws Exception {
        Optional<VendaCompraLojaVirtual> venda = Optional.of(vendaCompraLojaVirtualRepository.findById(objetoPostCarneJuno.getIdVenda()).get());

        CobrancaAPIAsaas cobrancaAPIAsaas = new CobrancaAPIAsaas();
        cobrancaAPIAsaas.setCustomer(this.buscaClientePessoaApiAsaas(objetoPostCarneJuno));
        cobrancaAPIAsaas.setBillingType("UNDEFINED");
        cobrancaAPIAsaas.setDescription("Pix ou Boleto gerado para cobrança, cód " + venda.get().getId());
        cobrancaAPIAsaas.setInstallmentValue(venda.get().getValorTotal().floatValue());
        cobrancaAPIAsaas.setInstallmentCount(1);

        Calendar dataVencimento = Calendar.getInstance();
        dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
        cobrancaAPIAsaas.setDueDate(new SimpleDateFormat("yyyy-MM-dd").format(dataVencimento.getTime()));

        cobrancaAPIAsaas.getInterest().setValue(1F);
        cobrancaAPIAsaas.getFine().setValue(1F);

        String json = new ObjectMapper().writeValueAsString(cobrancaAPIAsaas);

        Client cliente = new HostIgnoreClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringCliente();
        WebResource webResource = cliente.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "payments");

        ClientResponse clientResponse = webResource
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token",ApiTokenIntegracao.KEY_API_ASSAS)
                .post(ClientResponse.class, json);
        
        String retorno = clientResponse.getEntity(String.class);
        clientResponse.close();

        LinkedHashMap<String, Object> parser = new JSONParser(retorno).parseObject();

        String installment = parser.get("installment").toString();
        Client cliente2 = new HostIgnoreClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringCliente();
        WebResource webResource2 = cliente2.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "payments?installment" + installment);

        ClientResponse clientResponse2 = webResource2
                .accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token",ApiTokenIntegracao.KEY_API_ASSAS)
                .get(ClientResponse.class);

          String retornoCobranca =  clientResponse2.getEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        CobrancaGeradaAsassApi listacobranca = objectMapper.readValue(retornoCobranca,
                new TypeReference<CobrancaGeradaAsassApi>() {});

        List<BoletoJuno> boletoJunos = new ArrayList<>();
        int recorrencia = 1;
        for (CobrancaGeradaAssasData data : listacobranca.getData()){
            BoletoJuno boletoJuno = new BoletoJuno();

            boletoJuno.setEmpresa(venda.get().getEmpresa());
            boletoJuno.setVendaCompraLojaVirtual(venda.get());
            boletoJuno.setCode(data.getId());
            boletoJuno.setLink(data.getInvoiceUrl());
            boletoJuno.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(data.getDueDate())));
            boletoJuno.setCheckoutUrl(data.getInvoiceUrl());
            boletoJuno.setValor(new BigDecimal(data.getValue()));
            boletoJuno.setIdChrBoleto(data.getId());
            boletoJuno.setInstallmentLink(data.getInvoiceUrl());
            boletoJuno.setRecorrencia(recorrencia);


            ObjetoQrCodePixAsaas codePixAsaas = this.buscarQrCodeCodigoPix(data.getId());

            //boletoJuno.setIdPix(data.getPixQrCodeId());
            boletoJuno.setPayloadInBase64(codePixAsaas.getPayload());
            boletoJuno.setImageInBase64(codePixAsaas.getEncodedImage());
            boletoJunos.add(boletoJuno);
            recorrencia ++;

        }
        boletoJunoRepository.saveAllAndFlush(boletoJunos);

        return boletoJunos.get(0).getCheckoutUrl();
    }

    /**
     * suporte@jdetreinamento.com.br
     59dwed9898sd8
     * Retorna o id do Customer (Pessoa/cliente)
     */
    public String buscaClientePessoaApiAsaas(ObjetoPostCarneJuno dados) throws Exception {

        /*id do clinete para ligar com a conbrança*/
        String customer_id = "";

        /*--------------INICIO - criando ou consultando o cliente*/

        Client client = new HostIgnoreClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringCliente();
        WebResource webResource = client.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "customers?email="+dados.getEmail());

        ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token",ApiTokenIntegracao.KEY_API_ASSAS)
                .get(ClientResponse.class);

        LinkedHashMap<String, Object> parser = new JSONParser(clientResponse.getEntity(String.class)).parseObject();
        clientResponse.close();
        Integer total = Integer.parseInt(parser.get("totalCount").toString());

        if (total <= 0) { /*Cria o cliente*/

            ClienteAsaasApiPagamento clienteAsaasApiPagamento = new ClienteAsaasApiPagamento();

            if (!ValidateCPF.isCPF(dados.getPayerCpfCnpj())) {
                clienteAsaasApiPagamento.setCpfCnpj("60051803046");
            }else {
                clienteAsaasApiPagamento.setCpfCnpj(dados.getPayerCpfCnpj());
            }

            clienteAsaasApiPagamento.setEmail(dados.getEmail());
            clienteAsaasApiPagamento.setName(dados.getPayerName());
            clienteAsaasApiPagamento.setPhone(dados.getPayerPhone());

            Client client2 = new HostIgnoreClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringCliente();
            WebResource webResource2 = client2.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "customers");

            ClientResponse clientResponse2 = webResource2.accept("application/json;charset=UTF-8")
                    .header("Content-Type", "application/json")
                    .header("access_token",ApiTokenIntegracao.KEY_API_ASSAS)
                    .post(ClientResponse.class, new ObjectMapper().writeValueAsBytes(clienteAsaasApiPagamento));

            LinkedHashMap<String, Object> parser2 = new JSONParser(clientResponse2.getEntity(String.class)).parseObject();
            clientResponse2.close();

            customer_id = parser2.get("id").toString();

        }else {/*Já tem cliente cadastrado*/
            List<Object> data = (List<Object>) parser.get("data");
            customer_id = new Gson().toJsonTree(data.get(0)).getAsJsonObject().get("id").toString().replaceAll("\"", "");
        }

        return customer_id;


    }


    /**
     * Cria a chave da API Asass para o PIX;
     * @return Chave
     */
    public String criarChavePixAsaas() throws Exception {

        Client client = new HostIgnoreClient(AsaasApiPagamentoStatus.URL_API_ASAAS).hostIgnoringCliente();
        WebResource webResource = client.resource(AsaasApiPagamentoStatus.URL_API_ASAAS + "pix/addressKeys");

        ClientResponse clientResponse = webResource.accept("application/json;charset=UTF-8")
                .header("Content-Type", "application/json")
                .header("access_token", ApiTokenIntegracao.KEY_API_ASSAS)
                .post(ClientResponse.class, "{\"type\":\"EVP\"}");

        String strinRetorno = clientResponse.getEntity(String.class);
        clientResponse.close();
        return strinRetorno;

    }

}
