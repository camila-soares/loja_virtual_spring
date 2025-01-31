package com.br.loja.virtual.loja_virtual_spring.controller;


import com.br.loja.virtual.loja_virtual_spring.dto.ImagemProdutoDTO;
import com.br.loja.virtual.loja_virtual_spring.exceptions.ExceptinLojaVirtual;
import com.br.loja.virtual.loja_virtual_spring.model.ImagemProduto;
import com.br.loja.virtual.loja_virtual_spring.repository.ImagemProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Controller
public class ImagemProdutoController {

    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;


    @PostMapping(value = "/salvarImagemProduto")
    public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) throws ExceptinLojaVirtual {

        if (imagemProduto.getProduto().getId() == null || imagemProduto.getProduto().getId() <= 0) {
            throw new ExceptinLojaVirtual("Deve ser informado um produto");
        }

        if (imagemProduto.getEmpresa().getId() == null || imagemProduto.getEmpresa().getId() <= 0) {
            throw new ExceptinLojaVirtual("Deve ser informado um empresa");
        }
        ImagemProduto imagemProdutoSalvo = imagemProdutoRepository.saveAndFlush(imagemProduto);

        ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
        imagemProdutoDTO.setId(imagemProdutoSalvo.getId());
        imagemProdutoDTO.setProduto(imagemProdutoSalvo.getProduto().getId());
        imagemProdutoDTO.setEmpresa(imagemProdutoSalvo.getEmpresa().getId());
        imagemProdutoDTO.setImageMiniatura(imagemProdutoSalvo.getImageMiniatura());
        imagemProdutoDTO.setImageOriginal(imagemProdutoSalvo.getImageOriginal());

        return ResponseEntity.status(HttpStatus.CREATED).body(imagemProdutoDTO);
    }

    @DeleteMapping(value = "/deleteImagemProduto/{id}")
    public ResponseEntity<?> deleteImagemProduto(@PathVariable("id") Long id) throws ExceptinLojaVirtual {
        Optional<ImagemProduto> imagemProduto = Optional.ofNullable(imagemProdutoRepository.findById(id).orElseThrow(() -> new ExceptinLojaVirtual("imagem não encontrada.")));
        if(imagemProduto.isPresent()){
            imagemProdutoRepository.deleteById(id);
            return ResponseEntity.ok("Imagem deletada com sucesso!");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteTodasImagensDoProduto/{idProduto}")
    public ResponseEntity<?> deleteTodaImagensDoProduto(@PathVariable("idProduto") Long idProduto) throws ExceptinLojaVirtual {

        imagemProdutoRepository.deleteTodaImagensDoProduto(idProduto);

        return ResponseEntity.ok("Imagens deletadas com sucesso!");
    }

    @GetMapping(value = "/buscarImagensPorProduto/{idProduto}")
    public ResponseEntity<List<ImagemProdutoDTO>> listarImagensProduto(@PathVariable("idProduto") Long idProduto) {

        List<ImagemProdutoDTO> dtos = new ArrayList<>();
        List<ImagemProduto> imagemProdutos = imagemProdutoRepository.findImagemProdutoByIdProduto(idProduto);

        for (ImagemProduto imagemProduto : imagemProdutos) {
            ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
            imagemProdutoDTO.setId(imagemProduto.getId());
            imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
            imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
            imagemProdutoDTO.setImageMiniatura(imagemProduto.getImageMiniatura());
            imagemProdutoDTO.setImageOriginal(imagemProduto.getImageOriginal());

            dtos.add(imagemProdutoDTO);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarImagemProdutoPorId/{id}")
    public ResponseEntity<ImagemProdutoDTO> listarImagemProdutoPorId(@PathVariable("id") Long id) {

        Optional<ImagemProduto> imagemProdutos = Optional.ofNullable(imagemProdutoRepository.findById(id).orElse(null));

        ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
        imagemProdutoDTO.setId(imagemProdutos.get().getId());
        imagemProdutoDTO.setProduto(imagemProdutos.get().getProduto().getId());
        imagemProdutoDTO.setEmpresa(imagemProdutos.get().getEmpresa().getId());
        imagemProdutoDTO.setImageOriginal(imagemProdutos.get().getImageOriginal());
        imagemProdutoDTO.setImageMiniatura(imagemProdutos.get().getImageMiniatura());
        return new ResponseEntity<>(imagemProdutoDTO, HttpStatus.OK);
    }
}
