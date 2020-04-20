package br.com.basis.madre.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Especialidade entity.
 */
public class EspecialidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String sigla;

    @NotNull
    private String especialidade;

    private Long equipeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EspecialidadeDTO especialidadeDTO = (EspecialidadeDTO) o;
        if (especialidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), especialidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EspecialidadeDTO{" +
            "id=" + getId() +
            ", sigla='" + getSigla() + "'" +
            ", especialidade='" + getEspecialidade() + "'" +
            ", equipe=" + getEquipeId() +
            "}";
    }
}
