package com.br.loja.virtual.loja_virtual_spring.dto.calculofrete;

import lombok.Data;

import java.io.Serializable;


@Data
public class EmpresaTransporteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nome;
	private String valor;
	private String empresa;
	private String picture;

	public boolean dadosOK() {

		if (id != null && empresa != null && valor != null && nome != null) {
			return true;
		}

		return false;
	}

}
