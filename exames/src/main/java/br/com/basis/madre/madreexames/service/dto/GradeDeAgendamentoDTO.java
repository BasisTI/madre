package br.com.basis.madre.madreexames.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.GradeDeAgendamento} entity.
 */
public class GradeDeAgendamentoDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String grade;

    @NotNull
    private Integer unidadeExecutoraId;

    private Integer responsavelId;

    @NotNull
    private Boolean ativo;


    private Long gradeDeAgendamentoId;

    private String gradeDeAgendamentoGrade;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public Long getGradeDeAgendamentoId() {
        return gradeDeAgendamentoId;
    }

    public void setGradeDeAgendamentoId(Long salaId) {
        this.gradeDeAgendamentoId = salaId;
    }

    public String getGradeDeAgendamentoGrade() {
        return gradeDeAgendamentoGrade;
    }

    public void setGradeDeAgendamentoGrade(String salaGrade) {
        this.gradeDeAgendamentoGrade = salaGrade;
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
            ", grade='" + getGrade() + "'" +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            ", responsavelId=" + getResponsavelId() +
            ", ativo='" + isAtivo() + "'" +
            ", gradeDeAgendamentoId=" + getGradeDeAgendamentoId() +
            ", gradeDeAgendamentoGrade='" + getGradeDeAgendamentoGrade() + "'" +
            "}";
    }
}