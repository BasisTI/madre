package br.com.basis.madre.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CRM entity.
 */
public class CRMDTO implements Serializable {

    private Long id;

    @NotNull
    private Long codigo;

    @NotNull
    private String nome;

    private Long solicitacaoDeInternacaoId;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getSolicitacaoDeInternacaoId() {
        return solicitacaoDeInternacaoId;
    }

    public void setSolicitacaoDeInternacaoId(Long solicitacaoDeInternacaoId) {
        this.solicitacaoDeInternacaoId = solicitacaoDeInternacaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CRMDTO cRMDTO = (CRMDTO) o;
        if (cRMDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cRMDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CRMDTO{" +
            "id=" + getId() +
            ", codigo=" + getCodigo() +
            ", nome='" + getNome() + "'" +
            ", solicitacaoDeInternacao=" + getSolicitacaoDeInternacaoId() +
            "}";
    }
}
