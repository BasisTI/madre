package br.com.basis.madre.seguranca.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import br.com.basis.madre.seguranca.domain.enumeration.SituacaoGraduacao;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link br.com.basis.madre.seguranca.domain.Graduacao} entity.
 */
public class GraduacaoDTO implements Serializable {

    private Long id;

    private String curso;

    @NotNull
    private SituacaoGraduacao situacao;

    @NotNull
    private LocalDate anoInicio;

    private LocalDate anoFim;

    private String semestre;

    private String nroRegConselho;


    private Long servidorId;

    private Long tiposDeQualificacaoId;

    private Long instituicaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public SituacaoGraduacao getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoGraduacao situacao) {
        this.situacao = situacao;
    }

    public LocalDate getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(LocalDate anoInicio) {
        this.anoInicio = anoInicio;
    }

    public LocalDate getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(LocalDate anoFim) {
        this.anoFim = anoFim;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getNroRegConselho() {
        return nroRegConselho;
    }

    public void setNroRegConselho(String nroRegConselho) {
        this.nroRegConselho = nroRegConselho;
    }

    public Long getServidorId() {
        return servidorId;
    }

    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    public Long getTiposDeQualificacaoId() {
        return tiposDeQualificacaoId;
    }

    public void setTiposDeQualificacaoId(Long tiposDeQualificacaoId) {
        this.tiposDeQualificacaoId = tiposDeQualificacaoId;
    }

    public Long getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Long instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraduacaoDTO)) {
            return false;
        }

        return id != null && id.equals(((GraduacaoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GraduacaoDTO{" +
            "id=" + getId() +
            ", curso='" + getCurso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", anoInicio='" + getAnoInicio() + "'" +
            ", anoFim='" + getAnoFim() + "'" +
            ", semestre='" + getSemestre() + "'" +
            ", nroRegConselho='" + getNroRegConselho() + "'" +
            ", servidorId=" + getServidorId() +
            ", tiposDeQualificacaoId=" + getTiposDeQualificacaoId() +
            ", instituicaoId=" + getInstituicaoId() +
            "}";
    }
}
