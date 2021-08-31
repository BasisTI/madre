package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.TipoDeMarcacao} entity.
 */
public class TipoDeMarcacaoDTO implements Serializable {
    
    private Long id;

    private String tipoDeMarcacao;

    @NotNull
    private Boolean ativo;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDeMarcacao() {
        return tipoDeMarcacao;
    }

    public void setTipoDeMarcacao(String tipoDeMarcacao) {
        this.tipoDeMarcacao = tipoDeMarcacao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeMarcacaoDTO)) {
            return false;
        }

        return id != null && id.equals(((TipoDeMarcacaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDeMarcacaoDTO{" +
            "id=" + getId() +
            ", tipoDeMarcacao='" + getTipoDeMarcacao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}