package br.com.basis.madre.madreexames.service.integracao;

import br.com.basis.madre.domain.UnidadeFuncional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("internacao")
public interface InternacaoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/unidades-funcionais/{id}")
    UnidadeFuncional getUnidadeFuncional(@PathVariable("id") long id);

}
