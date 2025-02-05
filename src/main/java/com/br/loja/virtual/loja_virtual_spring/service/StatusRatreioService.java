package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.model.StatusRatreio;
import com.br.loja.virtual.loja_virtual_spring.repository.StatusRastreioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusRatreioService {

    private final StatusRastreioRepository statusRastreioRepository;

    public StatusRatreioService(StatusRastreioRepository statusRastreioRepository) {
        this.statusRastreioRepository = statusRastreioRepository;
    }


    public List<StatusRatreio> consultarStatusRatreioPorVenda(Long idVenda) {
        List<StatusRatreio> list = statusRastreioRepository.findStatusRatreioByVenda(idVenda);

        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }
}
