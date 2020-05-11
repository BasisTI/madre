package br.com.basis.madre.prescricao.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.ViasAdministracao} entity.
 */
public class ViasAdministracaoDTO implements Serializable {

    private Long id;

    @Size(max = 80)
    private String descricao;

    private String sigla;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViasAdministracaoDTO viasAdministracaoDTO = (ViasAdministracaoDTO) o;
        if (viasAdministracaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), viasAdministracaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ViasAdministracaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
