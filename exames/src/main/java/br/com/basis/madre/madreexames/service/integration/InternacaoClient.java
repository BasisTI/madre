package br.com.basis.madre.madreexames.service.integration;

import br.com.basis.madre.madreexames.service.dto.integracao.UnidadeFuncionalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("internacao")
public interface InternacaoClient {

    @GetMapping(value = "/api/unidades-funcionais/{id}")
    UnidadeFuncionalDTO getUnidadeFuncional(@PathVariable("id") long id);

}
