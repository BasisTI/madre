package br.com.basis.madre.madreexames.service.dto;

import br.com.basis.madre.madreexames.domain.enumeration.Abrangencia;
import br.com.basis.madre.madreexames.domain.enumeration.Responsavel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.Recomendacao} entity.
 */
public class RecomendacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private Boolean avisoResponsavel;

    private Responsavel responsavel;

    private Abrangencia abrangencia;


    private Long materialId;

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

    public Boolean isAvisoResponsavel() {
        return avisoResponsavel;
    }

    public void setAvisoResponsavel(Boolean avisoResponsavel) {
        this.avisoResponsavel = avisoResponsavel;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Abrangencia getAbrangencia() {
        return abrangencia;
    }

    public void setAbrangencia(Abrangencia abrangencia) {
        this.abrangencia = abrangencia;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecomendacaoDTO)) {
            return false;
        }

        return id != null && id.equals(((RecomendacaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecomendacaoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", avisoResponsavel='" + isAvisoResponsavel() + "'" +
            ", responsavel='" + getResponsavel() + "'" +
            ", abrangencia='" + getAbrangencia() + "'" +
            ", materialId=" + getMaterialId() +
            "}";
    }
}
