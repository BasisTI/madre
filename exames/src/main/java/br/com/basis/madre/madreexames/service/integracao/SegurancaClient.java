package br.com.basis.madre.madreexames.service.integracao;

import br.com.basis.madre.seguranca.domain.Servidor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("madreseguranca")
public interface SegurancaClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/servidors/{id}")
    Servidor getServidor(@PathVariable("id") long id);

}

