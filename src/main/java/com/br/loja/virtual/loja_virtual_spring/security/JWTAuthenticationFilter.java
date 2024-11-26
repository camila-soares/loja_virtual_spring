package com.br.loja.virtual.loja_virtual_spring.security;

import com.sun.xml.bind.v2.TODO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/** filtro de todas  as requisicoes serão capturada para autenticar*/
public class JWTAuthenticationFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException {

        try {

            /** Estabelece autenticacao do usuario*/
            Authentication authentication = new JWTTokenAuthenticationService()
                    .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

            /** coloca o processo de autenticacao para spring security*/
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        }catch (Exception e ){
            e.printStackTrace();
            response.getWriter().write("Ocorreu um erri no sistema, avise ao administrador: \n" + e.getMessage());
        }
    }
}
