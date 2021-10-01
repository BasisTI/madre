package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Sala} entity.
 */
public class SalaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String locacao;

    @NotNull
    private Boolean ativo;

    private Integer unidadeExecutoraId;

    
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

    public String getLocacao() {
        return locacao;
    }

    public void setLocacao(String locacao) {
        this.locacao = locacao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalaDTO)) {
            return false;
        }

        return id != null && id.equals(((SalaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", locacao='" + getLocacao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            "}";
    }
}
