package br.com.basis.suprimentos.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.suprimentos.domain.CaracteristicaArmazenamento} entity.
 */
public class CaracteristicaArmazenamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean misturaMateriaisDireitos;

    @NotNull
    private Boolean misturaGrupoMateriais;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMisturaMateriaisDireitos() {
        return misturaMateriaisDireitos;
    }

    public void setMisturaMateriaisDireitos(Boolean misturaMateriaisDireitos) {
        this.misturaMateriaisDireitos = misturaMateriaisDireitos;
    }

    public Boolean isMisturaGrupoMateriais() {
        return misturaGrupoMateriais;
    }

    public void setMisturaGrupoMateriais(Boolean misturaGrupoMateriais) {
        this.misturaGrupoMateriais = misturaGrupoMateriais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaracteristicaArmazenamentoDTO caracteristicaArmazenamentoDTO = (CaracteristicaArmazenamentoDTO) o;
        if (caracteristicaArmazenamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caracteristicaArmazenamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaracteristicaArmazenamentoDTO{" +
            "id=" + getId() +
            ", misturaMateriaisDireitos='" + isMisturaMateriaisDireitos() + "'" +
            ", misturaGrupoMateriais='" + isMisturaGrupoMateriais() + "'" +
            "}";
    }
}
