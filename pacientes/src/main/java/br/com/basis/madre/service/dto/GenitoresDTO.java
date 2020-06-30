package br.com.basis.madre.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Genitores} entity.
 */
public class GenitoresDTO implements Serializable {

    private Long id;

    private String prontuarioDaMae;

    @NotNull
    private String nomeDaMae;

    @NotNull
    private String nomeDoPai;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProntuarioDaMae() {
        return prontuarioDaMae;
    }

    public void setProntuarioDaMae(String prontuarioDaMae) {
        this.prontuarioDaMae = prontuarioDaMae;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GenitoresDTO genitoresDTO = (GenitoresDTO) o;
        if (genitoresDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genitoresDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GenitoresDTO{" +
            "id=" + getId() +
            ", prontuarioDaMae='" + getProntuarioDaMae() + "'" +
            ", nomeDaMae='" + getNomeDaMae() + "'" +
            ", nomeDoPai='" + getNomeDoPai() + "'" +
            "}";
    }
}
