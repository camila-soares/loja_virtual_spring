package com.br.loja.virtual.loja_virtual_spring.dto.cobrancas;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal retorno da cobrança da API Asaas
 * @author camila
 *
 */
@Data
public class CobrancaGeradaAsassApi {

	private String object;
	private Boolean hasMore;
	private Integer totalCount;
	private Integer limit;
	private Integer offset;

	private List<CobrancaGeradaAssasData> data = new ArrayList<CobrancaGeradaAssasData>();


}
