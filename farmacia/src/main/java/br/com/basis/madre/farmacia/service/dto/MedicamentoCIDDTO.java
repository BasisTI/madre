package br.com.basis.madre.farmacia.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.basis.madre.farmacia.domain.MedicamentoCID} entity.
 */
public class MedicamentoCIDDTO implements Serializable {

    private Long id;

    private String codigoMedicamento;

    private String codigoCID;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    public String getCodigoCID() {
        return codigoCID;
    }

    public void setCodigoCID(String codigoCID) {
        this.codigoCID = codigoCID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicamentoCIDDTO medicamentoCIDDTO = (MedicamentoCIDDTO) o;
        if (medicamentoCIDDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicamentoCIDDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicamentoCIDDTO{" +
            "id=" + getId() +
            ", codigoMedicamento='" + getCodigoMedicamento() + "'" +
            ", codigoCID='" + getCodigoCID() + "'" +
            "}";
    }
}
