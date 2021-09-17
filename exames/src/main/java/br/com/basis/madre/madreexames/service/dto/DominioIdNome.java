
package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;

public class DominioIdNome {


    private Long id;

    @NotNull
    private String nome;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
