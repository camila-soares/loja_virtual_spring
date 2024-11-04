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

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }
}
