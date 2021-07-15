package br.com.basis.madre.exames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.exames.domain.InformacoesComplementares} entity.
 */
public class InformacoesComplementaresDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer prontuario;

    private Integer codigo;


    private Long atendimentoDiversoId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProntuario() {
        return prontuario;
    }

    public void setProntuario(Integer prontuario) {
        this.prontuario = prontuario;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Long getAtendimentoDiversoId() {
        return atendimentoDiversoId;
    }

    public void setAtendimentoDiversoId(Long atendimentoDiversoId) {
        this.atendimentoDiversoId = atendimentoDiversoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformacoesComplementaresDTO)) {
            return false;
        }

        return id != null && id.equals(((InformacoesComplementaresDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformacoesComplementaresDTO{" +
            "id=" + getId() +
            ", prontuario=" + getProntuario() +
            ", codigo=" + getCodigo() +
            ", atendimentoDiversoId=" + getAtendimentoDiversoId() +
            "}";
    }
}
