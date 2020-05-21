package br.com.basis.madre.prescricao.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.prescricao.domain.TipoItemDieta} entity.
 */
public class TipoItemDietaDTO implements Serializable {

    private Long id;

    @Size(max = 80)
    private String descricao;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoItemDietaDTO tipoItemDietaDTO = (TipoItemDietaDTO) o;
        if (tipoItemDietaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoItemDietaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoItemDietaDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
