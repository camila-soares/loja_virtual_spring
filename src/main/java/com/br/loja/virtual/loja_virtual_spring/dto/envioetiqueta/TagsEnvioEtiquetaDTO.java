package com.br.loja.virtual.loja_virtual_spring.dto.envioetiqueta;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagsEnvioEtiquetaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tag;
    private String url;
}
