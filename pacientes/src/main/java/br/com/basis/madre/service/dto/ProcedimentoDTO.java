package br.com.basis.madre.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Procedimento entity.
 */
public class ProcedimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long codigo;

    @NotNull
    private String procedimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcedimentoDTO procedimentoDTO = (ProcedimentoDTO) o;
        if (procedimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcedimentoDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", procedimento='" + getProcedimento() + "'" +
            "}";
    }
}
