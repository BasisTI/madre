package br.com.basis.madre.madreexames.service.integration;

import br.com.basis.madre.madreexames.service.dto.integracao.ServidorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("madreseguranca")
public interface SegurancaClient {

    @GetMapping(value = "/api/servidors/{id}")
    ServidorDTO getServidor(@PathVariable("id") long id);

}

