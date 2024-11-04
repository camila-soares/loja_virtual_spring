package com.br.loja.virtual.loja_virtual_spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAuthenticationService {

    private static final long EXPIRATION_TIME = 959990000;

    private static final String SECRET = "loja_virtual_spring_secret";

    private static final String TOKEN_PREFIX = "Bearer";
    public static final String AUTHORIZATION = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws IOException {

        /*Montar o token */
        var JWT = Jwts.builder().//chama o gerador de token
        setSubject(username) //adiciona o user
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();// tempo de expiracao

        var token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(AUTHORIZATION, token);

        liberacaoCors(response);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    private void liberacaoCors(HttpServletResponse response) throws IOException {

        if( response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        };

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }

    }
}
