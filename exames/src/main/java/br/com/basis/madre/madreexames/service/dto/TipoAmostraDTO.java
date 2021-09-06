package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.TipoAmostra} entity.
 */
public class TipoAmostraDTO implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoAmostraDTO)) {
            return false;
        }

        return id != null && id.equals(((TipoAmostraDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoAmostraDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
