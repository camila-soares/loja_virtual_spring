package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
import com.br.loja.virtual.loja_virtual_spring.repository.ProdutoRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Component
public class ScheduleAutomaticService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private SendEmailService sendEmailService;


    @Scheduled(cron = "0 0 9 * * Mon", zone = "America/Sao_Paulo")
    public void notifyEstoqueProdutoBsixo(){
        List<Produto> produtos = produtoRepository.findByEstoqueBaixo();

        for(Produto produto : produtos) {
            if (produto.getAlertaQtdeEstoque() && produto.getQdtEstoque() <= 5) {
                StringBuilder message = new StringBuilder();
                message.append("Olá, ").append(produto.getEmpresa().getNome()).append("<br/>");
                message.append("Seu estoque do produto ").append(produto.getNome()).append(" está baixo ").append("<br/>");
                message.append("Código do produto: ").append(produto.getId()).append("<br/>");
                message.append(" Quantidade atual no estoque ").append(produto.getQdtEstoque()).append("<br/>");
                message.append("atualize seu estoque ");

                try {
                    sendEmailService.enviarEmailHtmlLoginESenha("Atualização de Estoque", String.valueOf(message), produto.getEmpresa().getEmail());
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    //@Scheduled(initialDelay = 2000, fixedDelay = 86400000 /*Roda a cada 24h*/)
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo" /*Roda todos dia as 11 da manhã*/)
    public void notifyUSerUpdatePassword(){

        //cron = "0 0 18 * * *"
       List<Usuario> usuarios = usuarioRepository.userPasswordDefeated();

       for (Usuario usuario : usuarios) {

           StringBuilder message = new StringBuilder();
           message.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
           message.append("Está na hora de trocar sua senha já passou 90 dias de validade.").append("<br/>");
           message.append("Troque sua senha de acesso da Loja virtual Camila - JDEV ");

           try {
               sendEmailService.enviarEmailHtmlLoginESenha("Atualização de senha", String.valueOf(message), usuario.getLogin()
               );
           } catch (MessagingException | UnsupportedEncodingException e) {
               throw new RuntimeException(e);
           }

       }



    }
}
