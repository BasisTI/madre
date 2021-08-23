package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.GradeDeAgendamento} entity.
 */
public class GradeDeAgendamentoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer unidadeExecutoraId;

    private Integer responsavelId;

    @NotNull
    private Boolean ativo;


    private Long exameGradeId;

    private String exameGradeNome;

    private Long salaGradeId;

    private String salaGradeIdentificacaoDaSala;

    private Long grupoAgendamentoExameId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    public Integer getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getExameGradeId() {
        return exameGradeId;
    }

    public void setExameGradeId(Long exameId) {
        this.exameGradeId = exameId;
    }

    public String getExameGradeNome() {
        return exameGradeNome;
    }

    public void setExameGradeNome(String exameNome) {
        this.exameGradeNome = exameNome;
    }

    public Long getSalaGradeId() {
        return salaGradeId;
    }

    public void setSalaGradeId(Long salaId) {
        this.salaGradeId = salaId;
    }

    public String getSalaGradeIdentificacaoDaSala() {
        return salaGradeIdentificacaoDaSala;
    }

    public void setSalaGradeIdentificacaoDaSala(String salaIdentificacaoDaSala) {
        this.salaGradeIdentificacaoDaSala = salaIdentificacaoDaSala;
    }

    public Long getGrupoAgendamentoExameId() {
        return grupoAgendamentoExameId;
    }

    public void setGrupoAgendamentoExameId(Long grupoAgendamentoExameId) {
        this.grupoAgendamentoExameId = grupoAgendamentoExameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeDeAgendamentoDTO)) {
            return false;
        }

        return id != null && id.equals(((GradeDeAgendamentoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradeDeAgendamentoDTO{" +
            "id=" + getId() +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            ", responsavelId=" + getResponsavelId() +
            ", ativo='" + isAtivo() + "'" +
            ", exameGradeId=" + getExameGradeId() +
            ", exameGradeNome='" + getExameGradeNome() + "'" +
            ", salaGradeId=" + getSalaGradeId() +
            ", salaGradeIdentificacaoDaSala='" + getSalaGradeIdentificacaoDaSala() + "'" +
            ", grupoAgendamentoExameId=" + getGrupoAgendamentoExameId() +
            "}";
    }
}
