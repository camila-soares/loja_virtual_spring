package com.br.loja.virtual.loja_virtual_spring.dto.cobrancas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CobrancaGeradaAssasData {

	private String object;
	private String id;
	private String dateCreated;
	private String customer;
	private String paymentLink = null;
	private String dueDate;
	private float value;
	private float netValue;
	private String billingType;
	private String pixTransaction;
	private String status;
	private String description = null;
	private String installment = null;
	private String originalValue = null;
	private String externalReference = null;
	private float interestValue;
	private String confirmedDate;
	private String pixQrCodeId;
	private String originalDueDate;
	private String paymentDate;
	private String clientPaymentDate;
	private String installmentNumber = null;
	private String invoiceUrl;
	private String invoiceNumber;
	private boolean deleted;
	private boolean anticipated;
	private boolean anticipable;
	private String creditDate;
	private String estimatedCreditDate = null;
	private String transactionReceiptUrl;
	private String nossoNumero;
	private String bankSlipUrl = null;
	private String lastInvoiceViewedDate;
	private String lastBankSlipViewedDate = null;
	private Boolean postalService = false;
	private CobrancaoGeradaSaasDiscount discount = new CobrancaoGeradaSaasDiscount();
	private CobrancaoGeradaSaasFine fine = new CobrancaoGeradaSaasFine();
	private CobrancaoGeradaSaasInterest interest = new CobrancaoGeradaSaasInterest();


}
