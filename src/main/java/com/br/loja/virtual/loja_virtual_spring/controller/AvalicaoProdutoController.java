package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.dto.AvaliacaoProdutoDTO;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.AvaliacaoProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.AvaliacaoProdutoRepository;
import com.br.loja.virtual.loja_virtual_spring.service.AvaliacaoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class AvalicaoProdutoController {

    @Autowired
    private AvaliacaoProdutoService avaliacaoProdutoService;

    @Autowired
    private AvaliacaoProdutoRepository avaliacaoProdutoRepository;


    @PostMapping(value = "/criaAvaliacaoProduto")
    public ResponseEntity<AvaliacaoProduto> createAvaliacaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacaoProduto) throws ExceptinLojaVirtual {

      AvaliacaoProduto avaliacaoProdutosave = avaliacaoProdutoService.save(avaliacaoProduto);

        return new ResponseEntity<>(avaliacaoProdutosave, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/listarTodasValiacoes")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> listarTodasValiacoes() {
        List<AvaliacaoProduto> avaliacoes = avaliacaoProdutoRepository.findAll();
        List<AvaliacaoProdutoDTO> avaliacoesDTO = new ArrayList<>();

        getIterableResponseEntity(avaliacoes, avaliacoesDTO);
        return new ResponseEntity<>(avaliacoesDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteAvaliacaoDoProdutoPorId/{id}")
    public ResponseEntity<String> deleteAvaliacaoProduto(@PathVariable Long id) {
        Optional<AvaliacaoProduto> avaliacaoProduto = avaliacaoProdutoRepository.findById(id);
        var a = avaliacaoProduto.get();
        if (avaliacaoProduto.isPresent()) {
            avaliacaoProdutoRepository.delete(avaliacaoProduto.get());
        }
        return new ResponseEntity<>("Avaliacao deleta por usuário " + a.getPessoa().getNome() ,HttpStatus.OK);
    }

    private ResponseEntity<List<AvaliacaoProdutoDTO>> getIterableResponseEntity(List<AvaliacaoProduto> avaliacoes, List<AvaliacaoProdutoDTO> avaliacoesDTO) {
        for (AvaliacaoProduto avaliacaoProduto : avaliacoes) {

            AvaliacaoProdutoDTO avaliacaoProdutoDTO = new AvaliacaoProdutoDTO();
            avaliacaoProdutoDTO.setId(avaliacaoProduto.getId());
            avaliacaoProdutoDTO.setDescricao(avaliacaoProduto.getDescricao());
            avaliacaoProdutoDTO.setNota(avaliacaoProduto.getNota());
            avaliacaoProdutoDTO.setPessoa(avaliacaoProduto.getPessoa().getId());
            avaliacaoProdutoDTO.setEmpresa(avaliacaoProduto.getEmpresa().getId());
            avaliacaoProdutoDTO.setProduto(avaliacaoProduto.getProduto().getId());
            avaliacoesDTO.add(avaliacaoProdutoDTO);
        }
        return new ResponseEntity<>(avaliacoesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarAvaliacaoProdutoPorId/{id}")
    public ResponseEntity<AvaliacaoProdutoDTO> findAvaliacaoProdutoById(@PathVariable("id") Long id) throws ExceptinLojaVirtual {

        Optional<AvaliacaoProduto> avaliacaoProduto = avaliacaoProdutoRepository.findById(id);
        AvaliacaoProdutoDTO avaliacaoProdutoDTO = new AvaliacaoProdutoDTO();
        avaliacaoProdutoDTO.setId(avaliacaoProduto.get().getId());
        avaliacaoProdutoDTO.setDescricao(avaliacaoProduto.get().getDescricao());
        avaliacaoProdutoDTO.setNota(avaliacaoProduto.get().getNota());
        avaliacaoProdutoDTO.setPessoa(avaliacaoProduto.get().getPessoa().getId());
        avaliacaoProdutoDTO.setEmpresa(avaliacaoProduto.get().getEmpresa().getId());
        avaliacaoProdutoDTO.setProduto(avaliacaoProduto.get().getProduto().getId());
        if (!avaliacaoProduto.isPresent()) {
         throw new ExceptinLojaVirtual("Avaliacao do produto nao foi encontrada", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(avaliacaoProdutoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarAvaliacaoProdutoPorProduto/{idProduto}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> findAvaliacaoProdutoByProduto(@PathVariable("idProduto") Long idProduto) throws ExceptinLojaVirtual {

        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTOS = new ArrayList<>();
        List<AvaliacaoProduto> listaavaliacaoProduto = avaliacaoProdutoRepository.findAvalicaoProdutoByIdProduto(idProduto);
        if (listaavaliacaoProduto.isEmpty()) {
            throw new ExceptinLojaVirtual("Avaliacao do produto nao foi encontrada", HttpStatus.NOT_FOUND);
        }

        return getIterableResponseEntity(listaavaliacaoProduto, avaliacaoProdutoDTOS);
    }

    @GetMapping(value = "/buscarAvaliacaoProdutoPorPessoa/{idPessoa}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>> findAvaliacaoProdutoByPessoa(@PathVariable("idPessoa") Long idPessoa) throws ExceptinLojaVirtual {

        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTOS = new ArrayList<>();
        List<AvaliacaoProduto> listaavaliacaoProduto = avaliacaoProdutoRepository.findAvaliacaoProdutoByIdPessoa(idPessoa);
        if (listaavaliacaoProduto.isEmpty()) {
            throw new ExceptinLojaVirtual("Avaliacao do produto nao foi encontrada", HttpStatus.NOT_FOUND);
        }

        return getIterableResponseEntity(listaavaliacaoProduto, avaliacaoProdutoDTOS);
    }

    @ResponseBody
    @GetMapping(value = "/buscarAvaliacaoProdutoPessoa/{idProduto}/{idPessoa}")
    public ResponseEntity<List<AvaliacaoProdutoDTO>>
    avaliacaoProdutoPessoa(@PathVariable("idProduto") Long idProduto, @PathVariable("idPessoa") Long idPessoa) {
        List<AvaliacaoProdutoDTO> avaliacaoProdutoDTOS = new ArrayList<>();
        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.findAvaliacaoProdutoByIdProdutoAndIdPessoa(idProduto, idPessoa);
        getIterableResponseEntity(avaliacaoProdutos, avaliacaoProdutoDTOS);
        return new ResponseEntity<List<AvaliacaoProdutoDTO>>(avaliacaoProdutoDTOS,HttpStatus.OK);
    }
}
