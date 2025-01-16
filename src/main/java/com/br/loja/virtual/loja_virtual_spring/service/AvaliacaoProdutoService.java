package com.br.loja.virtual.loja_virtual_spring.service;

import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.AvaliacaoProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.AvaliacaoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoProdutoService {

    @Autowired
    private AvaliacaoProdutoRepository avaliacaoProdutoRepository;

    public AvaliacaoProduto save(AvaliacaoProduto avaliacaoProduto) throws ExceptinLojaVirtual {

        if (avaliacaoProduto.getId() == null) {
            if (avaliacaoProduto.getPessoa() == null || (avaliacaoProduto.getPessoa().getId() != null &&
                    avaliacaoProduto.getPessoa().getId() <= 0)) {
                throw new ExceptinLojaVirtual("A Avaliação deve conter uma pessoa ou cliente associado", HttpStatus.NOT_FOUND);
            } else if (avaliacaoProduto.getProduto() == null || (avaliacaoProduto.getProduto().getId() != null &&
                    avaliacaoProduto.getProduto().getId() <= 0)) {
                throw new ExceptinLojaVirtual("A Avaliação deve conter o produto associado", HttpStatus.NOT_FOUND);
            } else if (avaliacaoProduto.getEmpresa() == null || (avaliacaoProduto.getEmpresa().getId() != null &&
                    avaliacaoProduto.getEmpresa().getId() <= 0)) {
                throw new ExceptinLojaVirtual("A Avaliação deve conter uma empresa associada", HttpStatus.NOT_FOUND);

            }
        }
           return avaliacaoProdutoRepository.save(avaliacaoProduto);


    }
}
