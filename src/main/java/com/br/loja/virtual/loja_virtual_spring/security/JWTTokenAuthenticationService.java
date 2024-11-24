package com.br.loja.virtual.loja_virtual_spring.security;

import com.br.loja.virtual.loja_virtual_spring.ApplicationContextLoad;
import com.br.loja.virtual.loja_virtual_spring.model.Usuario;
import com.br.loja.virtual.loja_virtual_spring.repository.UsuarioRepository;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Service
@Component
public class JWTTokenAuthenticationService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    /**
     * Token de validade de 11 dias
     */
    private static final long EXPIRATION_TIME = 959990000;

    /**
     * Chave de senha para juntar com o JWT
     */
    private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";

    private static final String TOKEN_PREFIX = "Bearer";
    public static final String AUTHORIZATION = "Authorization";


    /** Gera o token e da a resposta para o cliente com o JWT*/
    public void addAuthentication(javax.servlet.http.HttpServletResponse response, String username) throws IOException {

        /*Montar o token */
        String JWT = Jwts.builder().//chama o gerador de token
                setSubject(username) //adiciona o user
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();// tempo de expiracao

        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(AUTHORIZATION, token);

        liberacaoCors(response);

        /*USado para */
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }


    /* Retorna o usuário validado com token ou caso nao seja validado retorna null*/
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

       String token = request.getHeader(AUTHORIZATION);
        try {
            if (token != null) {

                String tokenClean = token.replace(TOKEN_PREFIX, "").trim();

                /*faz validacao do token do usuário na requisição e obtem o USer*/
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenClean)
                        .getBody().getSubject();


                if (user != null) {

                    Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null) {
                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getPassword(),
                                usuario.getAuthorities());
                    }
                }
            }
        }catch (SignatureException e) {
            response.getWriter().write("Token está inválido.");

        }catch (ExpiredJwtException e) {
            response.getWriter().write("Token está expirado, efetue o login novamente.");
        }
        finally {
            liberacaoCors(response);
        }

        return null;

    }

    /* Fzendo liberacao comtra erro de Cors no navegador*/
    private void liberacaoCors(HttpServletResponse response) throws IOException {

        if( response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        };

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*``");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }

    }
}


