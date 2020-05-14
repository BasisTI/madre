package br.com.basis.madre.farmacia.service.dto;

import java.io.Serializable;

public class prescricaoDTO implements Serializable {

    private long id;
    private String unidade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
