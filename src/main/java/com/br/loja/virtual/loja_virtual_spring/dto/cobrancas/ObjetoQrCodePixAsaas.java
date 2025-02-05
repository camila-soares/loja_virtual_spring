package com.br.loja.virtual.loja_virtual_spring.dto.cobrancas;

import lombok.Data;

@Data
public class ObjetoQrCodePixAsaas {

	private String encodedImage;
	private String payload;
	private String expirationDate;
	private String success;


}
