package com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OptionsEnvioEtiquetaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String  insurance_value;
    private Boolean  receipt;
    private Boolean own_hand;
    private Boolean reverse;
    private Boolean non_commercial;

    private InvoiceEnvioEtiquetaDTO invoice = new InvoiceEnvioEtiquetaDTO();

    private String platform;

    private List<TagsEnvioEtiquetaDTO> tags = new ArrayList<>();

}
