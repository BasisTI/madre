package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.ProjetoDePesquisa} entity.
 */
public class ProjetoDePesquisaDTO extends DominioIdNome implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjetoDePesquisaDTO)) {
            return false;
        }

        return id != null && id.equals(((ProjetoDePesquisaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjetoDePesquisaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
