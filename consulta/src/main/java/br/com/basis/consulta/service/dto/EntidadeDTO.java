package br.com.basis.consulta.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.consulta.domain.Entidade} entity.
 */
public class EntidadeDTO implements Serializable {

    private Long id;

    private Long pacienteId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntidadeDTO entidadeDTO = (EntidadeDTO) o;
        if (entidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadeDTO{" +
            "id=" + getId() +
            ", pacienteId=" + getPacienteId() +
            "}";
    }
}
