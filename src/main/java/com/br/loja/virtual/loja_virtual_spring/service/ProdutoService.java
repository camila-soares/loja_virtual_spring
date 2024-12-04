package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import com.br.loja.virtual.loja_virtual_spring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public Produto criarProduto(Produto produto) throws ExceptinLojaVirtual {

        if (produto.getTipoUnidade() == null || produto.getTipoUnidade().trim().isEmpty()) {
            throw new ExceptinLojaVirtual("Tipo da unidade deve ser informada");
        }

        if (produto.getNome().length() < 10) {
            throw new ExceptinLojaVirtual("Nome do produto deve ter mais de 10 letras.");
        }


        if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Empresa responsável deve ser informada");
        }

        if (produto.getId() == null) {
            List<Produto> produtos  = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase(), produto.getEmpresa().getId());

            if (!produtos.isEmpty()) {
                throw new ExceptinLojaVirtual("Já existe Produto com a descrição: " + produto.getNome());
            }
        }

        Produto produtoSave = produtoRepository.save(produto);
        return produtoSave;
    }

}
