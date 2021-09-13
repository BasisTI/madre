package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;

public class DominioAtivo extends DominioIdNome {

    @NotNull
    private Boolean ativo;

    public Boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
