package br.com.basis.madre.madreexames.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Anticoagulante} entity.
 */
public class AnticoagulanteDTO extends DominioAtivo implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnticoagulanteDTO)) {
            return false;
        }

        return getId() != null && getId().equals(((AnticoagulanteDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnticoagulanteDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
