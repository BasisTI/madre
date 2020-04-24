package br.com.basis.madre.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Equipe entity.
 */
public class EquipeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long numeroDoConselho;

    @NotNull
    private String nome;

    private Long especialidadeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDoConselho() {
        return numeroDoConselho;
    }

    public void setNumeroDoConselho(Long numeroDoConselho) {
        this.numeroDoConselho = numeroDoConselho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Long especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipeDTO equipeDTO = (EquipeDTO) o;
        if (equipeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipeDTO{" +
            "id=" + getId() +
            ", numeroDoConselho=" + getNumeroDoConselho() +
            ", nome='" + getNome() + "'" +
            ", especialidade=" + getEspecialidadeId() +
            "}";
    }
}
