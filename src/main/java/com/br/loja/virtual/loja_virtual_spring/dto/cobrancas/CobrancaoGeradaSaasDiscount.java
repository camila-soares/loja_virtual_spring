package com.br.loja.virtual.loja_virtual_spring.dto.cobrancas;

import lombok.Data;

@Data
public class CobrancaoGeradaSaasDiscount {

	private Double value;
	private String limitDate;
	private Integer dueDateLimitDays;
	private String type;


}
