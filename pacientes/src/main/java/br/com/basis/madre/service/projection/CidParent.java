package br.com.basis.madre.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public interface CidParent {
    CID_ getParent();

    interface CID_ {
        Long getId();
        String getCodigo();
        String getDescricao();
    }
}
