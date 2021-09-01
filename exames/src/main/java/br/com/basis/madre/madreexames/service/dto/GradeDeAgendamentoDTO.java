package br.com.basis.madre.madreexames.service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.GradeDeAgendamento} entity.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-gradedeagendamento")
@AllArgsConstructor
@NoArgsConstructor
public class GradeDeAgendamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer unidadeExecutoraId;

    private Integer responsavelId;

    @NotNull
    private Boolean ativo;

    private Long exameGradeId;

    private String exameGradeNome;

    private Long grupoGradeId;

    private String grupoGradeNome;

    private Long salaGradeId;

    private String salaGradeIdentificacaoDaSala;

    private String responsavelNome;

    private String unidadeExecutoraNome;

    public GradeDeAgendamentoDTO(Long id, Integer unidadeExecutoraId, Integer responsavelId, Boolean ativo, Long exameGradeId, String exameGradeNome, Long grupoGradeId, String grupoGradeNome, Long salaGradeId, String salaGradeIdentificacaoDaSala) {
        this.id = id;
        this.unidadeExecutoraId = unidadeExecutoraId;
        this.responsavelId = responsavelId;
        this.ativo = ativo;
        this.exameGradeId = exameGradeId;
        this.exameGradeNome = exameGradeNome;
        this.grupoGradeId = grupoGradeId;
        this.grupoGradeNome = grupoGradeNome;
        this.salaGradeId = salaGradeId;
        this.salaGradeIdentificacaoDaSala = salaGradeIdentificacaoDaSala;
    }

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

    public Long getGrupoGradeId() {
        return grupoGradeId;
    }

    public void setGrupoGradeId(Long grupoGradeId) {
        this.grupoGradeId = grupoGradeId;
    }

    public String getGrupoGradeNome() {
        return grupoGradeNome;
    }

    public void setGrupoGradeNome(String grupoGradeNome) {
        this.grupoGradeNome = grupoGradeNome;
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

    public String getResponsavelNome() {
        return responsavelNome;
    }

    public void setResponsavelNome(String responsavelNome) {
        this.responsavelNome = responsavelNome;
    }

    public String getUnidadeExecutoraNome() {
        return unidadeExecutoraNome;
    }

    public void setUnidadeExecutoraNome(String unidadeExecutoraNome) {
        this.unidadeExecutoraNome = unidadeExecutoraNome;
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
            "id=" + id +
            ", unidadeExecutoraId=" + unidadeExecutoraId +
            ", responsavelId=" + responsavelId +
            ", ativo=" + ativo +
            ", exameGradeId=" + exameGradeId +
            ", exameGradeNome='" + exameGradeNome + '\'' +
            ", grupoGradeId=" + grupoGradeId +
            ", grupoGradeNome='" + grupoGradeNome + '\'' +
            ", salaGradeId=" + salaGradeId +
            ", salaGradeIdentificacaoDaSala='" + salaGradeIdentificacaoDaSala + '\'' +
            ", responsavelNome='" + responsavelNome + '\'' +
            ", unidadeExecutoraNome='" + unidadeExecutoraNome + '\'' +
            '}';
    }
}
