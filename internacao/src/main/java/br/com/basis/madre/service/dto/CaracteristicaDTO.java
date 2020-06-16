package br.com.basis.madre.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Caracteristica} entity.
 */
public class CaracteristicaDTO implements Serializable {

    private Long id;

    private String nome;


    private Long unidadeId;

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

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CaracteristicaDTO caracteristicaDTO = (CaracteristicaDTO) o;
        if (caracteristicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caracteristicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaracteristicaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", unidade=" + getUnidadeId() +
            "}";
    }
}
