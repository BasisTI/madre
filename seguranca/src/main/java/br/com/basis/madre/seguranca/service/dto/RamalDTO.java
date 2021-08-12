package br.com.basis.madre.seguranca.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Ramal} entity.
 */
public class RamalDTO implements Serializable {
    
    private Long id;

    private String numero;

    private Boolean urgencia;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean isUrgencia() {
        return urgencia;
    }

    public void setUrgencia(Boolean urgencia) {
        this.urgencia = urgencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RamalDTO)) {
            return false;
        }

        return id != null && id.equals(((RamalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RamalDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", urgencia='" + isUrgencia() + "'" +
            "}";
    }
}
