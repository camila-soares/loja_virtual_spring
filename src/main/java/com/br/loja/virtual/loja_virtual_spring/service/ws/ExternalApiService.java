package com.br.loja.virtual.loja_virtual_spring.service.ws;

import com.br.loja.virtual.loja_virtual_spring.dto.CEPDto;
import com.br.loja.virtual.loja_virtual_spring.dto.ConsultaCNPJDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Configuration
public class ExternalApiService {

    public CEPDto consultaCEP(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+cep+"/json/", CEPDto.class).getBody();

    }

    public ConsultaCNPJDto consultaCNPJDto(String cnpj) {

        return new RestTemplate()
                .getForEntity("https://receitaws.com.br/v1/cnpj/"+cnpj, ConsultaCNPJDto.class).getBody();

    }
}
