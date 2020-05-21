package br.com.basis.madre.farmacia.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.TipoMedicamento} entity.
 */
public class TipoMedicamentoDTO implements Serializable {

    private Long id;

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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoMedicamentoDTO tipoMedicamentoDTO = (TipoMedicamentoDTO) o;
        if (tipoMedicamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoMedicamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoMedicamentoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
