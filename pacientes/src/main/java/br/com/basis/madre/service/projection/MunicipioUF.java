package br.com.basis.madre.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public interface MunicipioUF {
    Long getId();
    String getNome();
    UF_ getUf();

    static interface UF_ {
        String getUnidadeFederativa();
        String getSigla();
    }
}
