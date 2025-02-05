package com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EnvioEtiquetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long service;
    private String agency;

    private FromEnvioEtiquetaDTO from = new FromEnvioEtiquetaDTO();

    private ToEnvioEtiquetaDTO to  = new ToEnvioEtiquetaDTO();

   private List<ProductsEnvioEtiquetaDTO> products = new ArrayList<>();

   private List<VolumesEnvioEtiquetaDTO> volumes =  new ArrayList<>();

   private OptionsEnvioEtiquetaDTO options = new OptionsEnvioEtiquetaDTO();




}
