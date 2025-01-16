package com.br.loja.virtual.loja_virtual_spring.service;


import com.br.loja.virtual.loja_virtual_spring.dto.CategoriaProdutoDto;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.CategoriaProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.CategoriaProdutoRepository;
import com.br.loja.virtual.loja_virtual_spring.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaProdutoService {

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    public CategoriaProdutoDto persistir(CategoriaProduto categoriaProduto) throws ExceptinLojaVirtual {

        if (categoriaProduto.getEmpresa().equals(null) || categoriaProduto.getEmpresa().getId().equals(null)){
            throw new ExceptinLojaVirtual("Empresa deve ser informada", HttpStatus.NOT_FOUND);
        }


    if (categoriaProdutoRepository.verifydescricaoExist(categoriaProduto.getNomeDesc().toUpperCase())) {
            throw new LayerInstantiationException("Já existe um categoria com esta descricao " + categoriaProduto.getNomeDesc());

    }

    categoriaProduto = categoriaProdutoRepository.save(categoriaProduto);

       CategoriaProdutoDto categoriaProdutoDto = new CategoriaProdutoDto();
    categoriaProdutoDto.setId(categoriaProduto.getId());
    categoriaProdutoDto.setNomeDesc(categoriaProduto.getNomeDesc());
    categoriaProdutoDto.setEmpresa(String.valueOf(categoriaProduto.getEmpresa().getId()));
        return categoriaProdutoDto;
    }

    public String deleteCategoriaProduto(CategoriaProduto categoriaProduto) throws ExceptinLojaVirtual {

       Optional<CategoriaProduto> categoriaProduto1 = categoriaProdutoRepository.findById(categoriaProduto.getId());
       if (categoriaProduto1.isPresent()) {
           categoriaProdutoRepository.delete(categoriaProduto);
       }else {
           throw new ExceptinLojaVirtual("NAO EXISTE", HttpStatus.NOT_FOUND);
       }
        return "Categoria produto deletado com sucesso";
    }

    public CategoriaProduto buscaCategoriaPorDesc(String desc) {

        return categoriaProdutoRepository.finByDescricao(desc);
    }

    public Iterable<CategoriaProduto> listaTodasCategorias() {

        return categoriaProdutoRepository.findAll();
    }
}
