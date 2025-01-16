package com.br.loja.virtual.loja_virtual_spring.dto;

import com.br.loja.virtual.loja_virtual_spring.model.Endereco;
import com.br.loja.virtual.loja_virtual_spring.model.Pessoa;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class VendaCompraLojaVirtualDTO {

	private Long id;

	private BigDecimal valorTotal = BigDecimal.ZERO;

	private BigDecimal valorDesc = BigDecimal.ZERO;

	private Pessoa pessoa;

	private Endereco cobranca;

	private Endereco entrega;

	private BigDecimal valorFrete = BigDecimal.ZERO;

	private List<ItemVendaDTO> itemVendaLoja = new ArrayList<ItemVendaDTO>();

}
