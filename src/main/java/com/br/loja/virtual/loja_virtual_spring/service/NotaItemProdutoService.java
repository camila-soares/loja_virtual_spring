package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.NotaItemProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.NotaItemProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaItemProdutoService {


    @Autowired
    private NotaItemProdutoRepository notaItemProdutoRepository;


    public NotaItemProduto salvar(NotaItemProduto notaItemProduto) throws ExceptinLojaVirtual {

        if (notaItemProduto.getId() == null) {
            if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
                throw new ExceptinLojaVirtual("Produto deve ser informado");
            }
            if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
                throw new ExceptinLojaVirtual("Nota fiscal deve ser informada");
            }
            if (notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
                throw new ExceptinLojaVirtual("Empresa deve ser informada");
            }
            List<NotaItemProduto> notaItemProdutos =
                    notaItemProdutoRepository.findNotaItemProdutoByProdutoAndNota(notaItemProduto.getProduto().getId(), notaItemProduto.getNotaFiscalCompra().getId());
            if (!notaItemProdutos.isEmpty()) {
                throw new ExceptinLojaVirtual("Já existe este produto cadastrado para esta nota!");
            }
        }

        return notaItemProdutoRepository.save(notaItemProduto);
    }

    public void deleteNotaItemPorId(Long id) {

        notaItemProdutoRepository.deleteById(id);
    }

    public NotaItemProduto buscarNotaItemProdutoPorId(Long id) throws ExceptinLojaVirtual {

        NotaItemProduto notaItemProduto = notaItemProdutoRepository.findById(id).orElse(null);

        if (notaItemProduto == null) {
            throw new ExceptinLojaVirtual("Não encontrou Nota Item produto com código: " + id);
        }

        return notaItemProduto;
    }
}
