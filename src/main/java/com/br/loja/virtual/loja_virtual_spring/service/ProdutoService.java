package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.PessoaJuridica;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaJuridicaRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;


    @Autowired
    private SendEmailService sendEmailService;


    public Produto criarProduto(Produto produto) throws ExceptinLojaVirtual, IOException {

        validateFields(produto);

        Produto produtoSave = produtoRepository.save(produto);

        if (produto.getAlertaQtdeEstoque() && produto.getQdtEstoque() <= 2){
            if (produto.getEmpresa() != null && produto.getEmpresa().getId() > 0) {

                Optional<PessoaJuridica> empresa =  pessoaJuridicaRepository.findById(produto.getEmpresa().getId());
                produto.getEmpresa().setEmail(empresa.get().getEmail());
            }

            sendEmailHtmlAlertaEstoqueProduto(produto);
        }

        return produtoSave;

    }

    public Produto buscarProdutoPorId(Long id) throws ExceptinLojaVirtual, IOException {
        Optional<Produto> produto = Optional.ofNullable(produtoRepository.findById(id).orElse(null));

        return produto.orElse(null);
    }

    private void validateFields(Produto produto) throws ExceptinLojaVirtual, IOException {
        if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Empresa responsável deve ser informada");
        }


        if (produto.getTipoUnidade() == null || produto.getTipoUnidade().trim().isEmpty()) {
            throw new ExceptinLojaVirtual("Tipo da unidade deve ser informada");
        }

        if (produto.getNome().length() < 10) {
            throw new ExceptinLojaVirtual("Nome do produto deve ter mais de 10 letras.");
        }

        if (produto.getId() == null) {
            List<Produto> produtos  = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase(), produto.getEmpresa().getId());

            if (!produtos.isEmpty()) {
                throw new ExceptinLojaVirtual("Já existe Produto com a descrição: " + produto.getNome());
            }
        }
        if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0) {
            throw new ExceptinLojaVirtual("Categoria do produto deve ser informada");
        }
        if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0) {
            throw new ExceptinLojaVirtual("Marca do produto deve ser informada");
        }
        if (produto.getQdtEstoque() < 1){
            throw new ExceptinLojaVirtual("Estoque do produto deve ter no minimo 1");
        }
        if (produto.getImagens() == null || produto.getImagens().isEmpty() || produto.getImagens().size() == 0) {
            throw new ExceptinLojaVirtual("Deve ser informada a imagens para o produto");
        }else if (produto.getImagens().size() < 3){
            throw new ExceptinLojaVirtual("Deve ser informada pelo menos 3 imagens para o produto");
        } else if (produto.getImagens().size() > 6){
            throw new ExceptinLojaVirtual("Deve ser informada no máximo 6 imagens para o produto");
        }



        if (produto.getId() == null){
            for (int x = 0; x < produto.getImagens().size(); x++){
                produto.getImagens().get(x).setProduto(produto);
                produto.getImagens().get(x).setEmpresa(produto.getEmpresa());

                String base64Image = "";
                if (produto.getImagens().get(x).getImageOriginal().contains("data:image")) {
                    base64Image = produto.getImagens().get(x).getImageOriginal().split(",")[1];
                }else {
                    base64Image = produto.getImagens().get(x).getImageOriginal();
                }

                byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

                if (bufferedImage != null) {
                    int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
                    int largura = Integer.parseInt("800");
                    int altura = Integer.parseInt("600");

                    BufferedImage resizedImage = new BufferedImage(largura, altura, type);
                    Graphics2D g = resizedImage.createGraphics();
                    g.drawImage(bufferedImage, 0, 0, largura, altura, null);
                    g.dispose();

                    //arrays de saida
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(resizedImage, "png", outputStream);

                    String miniImgBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(outputStream.toByteArray());

                    produto.getImagens().get(x).setImageMiniatura(miniImgBase64);

                    //descarrega os objetos
                    bufferedImage.flush();
                    resizedImage.flush();
                    outputStream.flush();
                    outputStream.close();
                }
            }
        }
    }

    private void sendEmailHtmlAlertaEstoqueProduto(Produto produto) {
        StringBuilder msgHtml = new StringBuilder();
        msgHtml.append("<h2>").append("Produto: ").append(produto.getNome()).append(" com estoque baixo: ").append(produto.getQdtEstoque());
        msgHtml.append("<p> Id Produto : ").append(produto.getId()).append("</p");
        msgHtml.append("</h2>");

        if (produto.getEmpresa().getEmail() != null) {
            try {

                sendEmailService.enviarEmailHtmlLoginESenha("Alerta quantidade de estoque", msgHtml.toString(), produto.getEmpresa().getEmail());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
