package br.com.basis.madre.seguranca.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import br.com.basis.madre.seguranca.domain.enumeration.TipoQualificacao;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.TiposDeQualificacao} entity.
 */
public class TiposDeQualificacaoDTO implements Serializable {
    
    private Long id;

    private String descricao;

    private TipoQualificacao tipo;

    private String conselho;

    @NotNull
    private Boolean situacao;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoQualificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoQualificacao tipo) {
        this.tipo = tipo;
    }

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) {
        this.conselho = conselho;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TiposDeQualificacaoDTO)) {
            return false;
        }

        return id != null && id.equals(((TiposDeQualificacaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TiposDeQualificacaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", conselho='" + getConselho() + "'" +
            ", situacao='" + isSituacao() + "'" +
            "}";
    }
}
