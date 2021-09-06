package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;

public class DominioComDescricao extends DominioBase {

    @NotNull
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
