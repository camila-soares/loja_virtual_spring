package com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta;

import lombok.Data;

import java.io.Serializable;


@Data
public class VolumesEnvioEtiquetaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;
}
