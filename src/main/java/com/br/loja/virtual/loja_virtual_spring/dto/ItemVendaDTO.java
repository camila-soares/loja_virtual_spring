package com.br.loja.virtual.loja_virtual_spring.dto;

import com.br.loja.virtual.loja_virtual_spring.model.Produto;
import lombok.Data;

@Data
public class ItemVendaDTO {

	private Double quantidade;

	private Produto produto;

}
