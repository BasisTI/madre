package br.com.basis.madre.seguranca.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public interface VinculoResumo {
    Long getId();
    String getDescricao();
    Long getMatricula();
    Long getCodigo();
}
