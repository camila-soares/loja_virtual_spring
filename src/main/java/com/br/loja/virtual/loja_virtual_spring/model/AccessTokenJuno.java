package com.br.loja.virtual.loja_virtual_spring.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "access_token_junoapi")
@SequenceGenerator(name = "seq_access_token_junoapi", sequenceName = "seq_access_token_junoapi", allocationSize = 1, initialValue = 1)
@Data
public class AccessTokenJuno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_access_token_junoapi")
    private Long id;

    @Column(columnDefinition = "text")
    private String access_token;

    private String token_type;

    private String expires_in;

    private String scope;

    private String user_name;

    private String jti;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at = Calendar.getInstance().getTime();

    public boolean expirado(){
        Date dataAtual = Calendar.getInstance().getTime();

        long tempo = dataAtual.getTime() - this.created_at.getTime();

        long minutos = (tempo / 1000) / 60; /* diferenca entre datas e horas inicial e final*/

        return (int) minutos > 50;

    }

}
