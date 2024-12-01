package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
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
    private SendEmailService sendEmailService;

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
