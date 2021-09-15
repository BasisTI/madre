package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.NotNull;

public class DominioComNome extends DominioBase {

    @NotNull
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
