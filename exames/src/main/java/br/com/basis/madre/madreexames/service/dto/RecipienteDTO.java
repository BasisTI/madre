package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Recipiente} entity.
 */
public class RecipienteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Boolean anticoagulante;

    @NotNull
    private Boolean ativo;

    
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

    public Boolean isAnticoagulante() {
        return anticoagulante;
    }

    public void setAnticoagulante(Boolean anticoagulante) {
        this.anticoagulante = anticoagulante;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipienteDTO)) {
            return false;
        }

        return id != null && id.equals(((RecipienteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipienteDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", anticoagulante='" + isAnticoagulante() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
