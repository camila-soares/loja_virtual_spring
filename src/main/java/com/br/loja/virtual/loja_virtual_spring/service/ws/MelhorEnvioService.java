package com.br.loja.virtual.loja_virtual_spring.service.ws;

import com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta.EnvioEtiquetaDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta.ProductsEnvioEtiquetaDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta.TagsEnvioEtiquetaDTO;
import com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta.VolumesEnvioEtiquetaDTO;
import com.br.loja.virtual.loja_virtual_spring.enums.ApiTokenIntegracao;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.ItemVendaLoja;
import com.br.loja.virtual.loja_virtual_spring.model.StatusRatreio;
import com.br.loja.virtual.loja_virtual_spring.model.VendaCompraLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.repository.StatusRastreioRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.VendaCompraLojaVirtualRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class MelhorEnvioService {

    private final VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;
    private final StatusRastreioRepository statusRastreioRepository;
    private final JdbcTemplate jdbcTemplate;

    public MelhorEnvioService(VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository, StatusRastreioRepository statusRastreioRepository, JdbcTemplate jdbcTemplate) {
        this.vendaCompraLojaVirtualRepository = vendaCompraLojaVirtualRepository;
        this.statusRastreioRepository = statusRastreioRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public String imprimeEtiquta(Long idVenda) throws ExceptinLojaVirtual, IOException {

        Optional<VendaCompraLojaVirtual> compra = vendaCompraLojaVirtualRepository.findById(idVenda);

        EnvioEtiquetaDTO envioEtiqueta = new EnvioEtiquetaDTO();
        envioEtiqueta.setService(3L);
        envioEtiqueta.setAgency(compra.get().getServicoTransportadora());

        envioEtiqueta.getFrom().setName(compra.get().getEmpresa().getNome());
        envioEtiqueta.getFrom().setPhone(compra.get().getEmpresa().getTelefone());
        envioEtiqueta.getFrom().setName(compra.get().getEmpresa().getEmail());
        envioEtiqueta.getFrom().setCompany_document("66398551000163");
        envioEtiqueta.getFrom().setState_register(compra.get().getEmpresa().getInscEstadual());
        envioEtiqueta.getFrom().setAddress(compra.get().getEnderecoCobranca().getLogradouro());
        envioEtiqueta.getFrom().setComplement(compra.get().getEnderecoCobranca().getComplemento());
        envioEtiqueta.getFrom().setNumber(compra.get().getEnderecoCobranca().getNumero());
        envioEtiqueta.getFrom().setDistrict(compra.get().getEnderecoCobranca().getBairro());
        envioEtiqueta.getFrom().setCity(compra.get().getEnderecoCobranca().getCidade());
        envioEtiqueta.getFrom().setCountry_id("BR");
        envioEtiqueta.getFrom().setPostal_code(compra.get().getEnderecoCobranca().getCep());
        envioEtiqueta.getFrom().setNote("ND");

        envioEtiqueta.getTo().setName(compra.get().getPessoa().getNome());
        envioEtiqueta.getTo().setPhone(compra.get().getPessoa().getTelefone());
        envioEtiqueta.getTo().setName(compra.get().getPessoa().getEmail());
        envioEtiqueta.getTo().setDocument(compra.get().getPessoa().getCpf());
        envioEtiqueta.getTo().setAddress(compra.get().getEnderecoEntrega().getLogradouro());
        envioEtiqueta.getTo().setComplement(compra.get().getEnderecoEntrega().getComplemento());
        envioEtiqueta.getTo().setNumber(compra.get().getEnderecoEntrega().getNumero());
        envioEtiqueta.getTo().setDistrict(compra.get().getEnderecoEntrega().getBairro());
        envioEtiqueta.getTo().setCity(compra.get().getEnderecoEntrega().getCidade());
        envioEtiqueta.getTo().setState_abbr(compra.get().getEnderecoEntrega().getUf());
        envioEtiqueta.getTo().setCountry_id("BR");
        envioEtiqueta.getTo().setPostal_code(compra.get().getEnderecoEntrega().getCep());
        envioEtiqueta.getTo().setNote("ND");

        List<ProductsEnvioEtiquetaDTO> products = new ArrayList<>();

        for (ItemVendaLoja itemVendaLoja : compra.get().getItemVendaLojas()){
            ProductsEnvioEtiquetaDTO productsEnvioEtiquetaDTO = new ProductsEnvioEtiquetaDTO();
            productsEnvioEtiquetaDTO.setName(itemVendaLoja.getProduto().getNome());
            productsEnvioEtiquetaDTO.setQuantity(itemVendaLoja.getQuantidade().toString());
            productsEnvioEtiquetaDTO.setUnitary_value("" + itemVendaLoja.getProduto().getValorVenda().doubleValue());

            products.add(productsEnvioEtiquetaDTO);
        }
        envioEtiqueta.setProducts(products);

        List<VolumesEnvioEtiquetaDTO> volumesEnriquetaDOS = new ArrayList<>();

        for (ItemVendaLoja itemVendaLoja : compra.get().getItemVendaLojas()){
            VolumesEnvioEtiquetaDTO  volumesDTO = new VolumesEnvioEtiquetaDTO();
            volumesDTO.setHeight(itemVendaLoja.getProduto().getAltura());
            volumesDTO.setLength(itemVendaLoja.getProduto().getProfundidade());
            volumesDTO.setWidth(itemVendaLoja.getProduto().getPeso());
            volumesDTO.setWeight(itemVendaLoja.getProduto().getLargura());

            volumesEnriquetaDOS.add(volumesDTO);
        }
        envioEtiqueta.setVolumes(volumesEnriquetaDOS);

        envioEtiqueta.getOptions().setInsurance_value("" + compra.get().getValorTotal().doubleValue());
        envioEtiqueta.getOptions().setReceipt(false);
        envioEtiqueta.getOptions().setOwn_hand(false);
        envioEtiqueta.getOptions().setReverse(false);
        envioEtiqueta.getOptions().setNon_commercial(false);
        envioEtiqueta.getOptions().getInvoice().setKey("31190307586261000184550010000092481404848162");
        envioEtiqueta.getOptions().setPlatform(compra.get().getEmpresa().getNomeFantasia());

        TagsEnvioEtiquetaDTO tags = new TagsEnvioEtiquetaDTO();

        tags.setTag("Identificacao do pedido na platamforma, exemplo: " + compra.get().getId());
        tags.setUrl(null);
        envioEtiqueta.getOptions().getTags().add(tags);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonEnvio = ow.writeValueAsString(envioEtiqueta);
        System.out.println("FORMATO_JSON " +jsonEnvio);


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonEnvio);
        Request request = new Request.Builder().
                url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/cart")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br").build();


        Response response = okHttpClient.newCall(request).execute();
        String retorno = response.body().string();
        System.out.println("RESPOSTA " +retorno.toString());
        if (retorno.contains("error")) {
            throw new ExceptinLojaVirtual(response.toString());
        }
        JsonNode jsonNode = new ObjectMapper().readTree(retorno);

        Iterator<JsonNode> iterator = jsonNode.iterator();

        String idEtiqueta = "";

        while (iterator.hasNext()) {
            JsonNode node = iterator.next();
            if (node.get("id") != null) {
                idEtiqueta = node.get("id").asText();
            }else {
                idEtiqueta = node.asText();
            }
            break;
        }
        vendaCompraLojaVirtualRepository.updateEtiqueta(idEtiqueta, compra.get().getId());

        OkHttpClient clientCompra = new OkHttpClient().newBuilder().build();
        MediaType mediaTypeCompra = MediaType.parse("application/json");
        RequestBody bodyCompra = RequestBody.create(mediaTypeCompra, "\n" +
                "{\n" +
                "    \"orders\":[\n" +
                "        \""+idEtiqueta+"\"\n" +
                "    ]\n" +
                "}");
        Request requestCOmpra = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/checkout")
                .method("POST", bodyCompra)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br").build();

        Response responseCompra = clientCompra.newCall(requestCOmpra).execute();


        System.out.println("RESPONSE COMPRA " + responseCompra.body().string());

        if (!responseCompra.isSuccessful()) {
            return new String("Nao foi possivel realizar a compra");
        }

        OkHttpClient clientGeraEtiqueta = new OkHttpClient().newBuilder().build();
        MediaType mediaTypeGeraEtiqueta = MediaType.parse("application/json");
        RequestBody bodyGeraEtiqueta = RequestBody.create(mediaTypeGeraEtiqueta, "{\n    \"orders\":[\n        \""+idEtiqueta+"\"\n    ]\n}");
        Request requestGeraEtiqueta = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX + "api/v2/me/shipment/generate")
                .method("POST", bodyGeraEtiqueta)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br").build();

        Response responseGeraEtiqueta = clientGeraEtiqueta.newCall(requestGeraEtiqueta).execute();

        System.out.println("RESPONSE GERAR " + responseGeraEtiqueta.body().string());

        if(!responseGeraEtiqueta.isSuccessful()) {
            return new String("Nao foi Gerar Etiqueta");
        }

        /*Faz impresão das etiquetas*/

        OkHttpClient clientImpressao = new OkHttpClient().newBuilder().build();
        okhttp3.MediaType mediaTypeImpressao = MediaType.parse("application/json");
        okhttp3.RequestBody bodyImressao = okhttp3.RequestBody.create(mediaTypeImpressao, "{\n    \"mode\": \"private\",\n    \"orders\": [\n        \""+idEtiqueta+"\"\n    ]\n}");
        okhttp3.Request requestImpressao = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX  + "api/v2/me/shipment/print")
                .method("POST", bodyImressao)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br")
                .build();

        okhttp3.Response responseImpressao = clientImpressao.newCall(requestImpressao).execute();

        if (!responseImpressao.isSuccessful()) {
            return new String("Nao foi possivel imprimir a etiqueta ");
        }
        String urlEtiqueta = responseImpressao.body().string();
        vendaCompraLojaVirtualRepository.updateUrlEtiquta(urlEtiqueta, compra.get().getId());

        OkHttpClient clientRastreio = new OkHttpClient().newBuilder().build();
        okhttp3.MediaType mediaTypeR = okhttp3.MediaType.parse("application/json");
        okhttp3.RequestBody bodyR = okhttp3.RequestBody.create(mediaTypeR, "{\n   " +
                " \"orders\": [\n        \""+idEtiqueta+"\"\n    ]\n}");
        okhttp3.Request requestR = new Request.Builder()
                .url(ApiTokenIntegracao.URL_MELHOR_ENVIO_SAND_BOX+ "api/v2/me/shipment/tracking")
                .method("POST", bodyR)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " +  ApiTokenIntegracao.TOKEN_MELHOR_ENVIO_SAND_BOX)
                .addHeader("User-Agent", "suporte@jdevtreinamento.com.br").build();

        Response responseRastreio = clientRastreio.newCall(requestR).execute();

        JsonNode jsonNodeRastreio = new ObjectMapper().readTree(responseRastreio.body().string());

        Iterator<JsonNode> iteratorR = jsonNodeRastreio.iterator();

        String idEtiquetaRastreio = "";

        while(iteratorR.hasNext()) {
            JsonNode node = iteratorR.next();
            if (node.get("melhorenvio_tracking") != null) {
                idEtiquetaRastreio = node.get("melhorenvio_tracking").asText();
            }else {
                idEtiquetaRastreio = node.asText();
            }
            break;
        }

        List<StatusRatreio> ratreios = statusRastreioRepository.findStatusRatreioByVenda(idVenda);

        if (ratreios.isEmpty()) {

            StatusRatreio ratreio = new StatusRatreio();
            ratreio.setEmpresa(compra.get().getEmpresa());
            ratreio.setVendaCompraLojaVirtual(compra.get());
            ratreio.setUrl_rastreio("https://www.melhorrastreio.com.br/rastreio/" + idEtiquetaRastreio);

            statusRastreioRepository.saveAndFlush(ratreio);
        }else {
            statusRastreioRepository.salvarurlRatreio("https://www.melhorrastreio.com.br/rastreio/" + idEtiquetaRastreio, idVenda);
        }

        System.out.println("TRACK"+idEtiquetaRastreio);

        return "Sucesso";

    }
}
