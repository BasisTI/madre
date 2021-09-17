package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;

public class DominioCodigo extends DominioIdNome {

    @NotNull
    private Integer codigo;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
}
