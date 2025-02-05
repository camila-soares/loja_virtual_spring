package com.br.loja.virtual.loja_virtual_spring.service.ws;


import com.br.loja.virtual.loja_virtual_spring.model.AccessTokenJuno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class AccessTokenJunoService {

    @PersistenceContext
    private EntityManager entityManager;

    public AccessTokenJuno buscarTOkenAtivo(){

        try {
            return (AccessTokenJuno) entityManager.createQuery("select a from AccessTokenJuno a").setMaxResults(1).getSingleResult();

        }catch (NoResultException e){
            return null;
        }
    }

}
