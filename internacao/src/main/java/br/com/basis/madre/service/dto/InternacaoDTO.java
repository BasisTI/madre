package br.com.basis.madre.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.madre.domain.enumeration.Prioridade;

/**
 * A DTO for the {@link br.com.basis.madre.domain.Internacao} entity.
 */
public class InternacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Prioridade prioridade;

    @NotNull
    private String justificativa;

    @NotNull
    private LocalDate dataDaInternacao;

    private Boolean diferencaDeClasse;

    private Boolean solicitarProntuario;


    private Long especialidadeId;

    private Long crmId;

    private Long hospitalDeOrigemId;

    private Long origemId;

    private Long convenioId;

    private Long planoId;

    private Long procedimentoId;

    private Long procedenciaId;

    private Long modalidadeAssistencialId;

    private Long localDeAtendimentoId;

    private Long caraterDaInternacaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public LocalDate getDataDaInternacao() {
        return dataDaInternacao;
    }

    public void setDataDaInternacao(LocalDate dataDaInternacao) {
        this.dataDaInternacao = dataDaInternacao;
    }

    public Boolean isDiferencaDeClasse() {
        return diferencaDeClasse;
    }

    public void setDiferencaDeClasse(Boolean diferencaDeClasse) {
        this.diferencaDeClasse = diferencaDeClasse;
    }

    public Boolean isSolicitarProntuario() {
        return solicitarProntuario;
    }

    public void setSolicitarProntuario(Boolean solicitarProntuario) {
        this.solicitarProntuario = solicitarProntuario;
    }

    public Long getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Long especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long cRMId) {
        this.crmId = cRMId;
    }

    public Long getHospitalDeOrigemId() {
        return hospitalDeOrigemId;
    }

    public void setHospitalDeOrigemId(Long hospitalId) {
        this.hospitalDeOrigemId = hospitalId;
    }

    public Long getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Long origemDaInternacaoId) {
        this.origemId = origemDaInternacaoId;
    }

    public Long getConvenioId() {
        return convenioId;
    }

    public void setConvenioId(Long convenioDeSaudeId) {
        this.convenioId = convenioDeSaudeId;
    }

    public Long getPlanoId() {
        return planoId;
    }

    public void setPlanoId(Long planoDeSaudeId) {
        this.planoId = planoDeSaudeId;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    public Long getProcedenciaId() {
        return procedenciaId;
    }

    public void setProcedenciaId(Long procedenciaId) {
        this.procedenciaId = procedenciaId;
    }

    public Long getModalidadeAssistencialId() {
        return modalidadeAssistencialId;
    }

    public void setModalidadeAssistencialId(Long modalidadeAssistencialId) {
        this.modalidadeAssistencialId = modalidadeAssistencialId;
    }

    public Long getLocalDeAtendimentoId() {
        return localDeAtendimentoId;
    }

    public void setLocalDeAtendimentoId(Long localDeAtendimentoId) {
        this.localDeAtendimentoId = localDeAtendimentoId;
    }

    public Long getCaraterDaInternacaoId() {
        return caraterDaInternacaoId;
    }

    public void setCaraterDaInternacaoId(Long caraterDaInternacaoId) {
        this.caraterDaInternacaoId = caraterDaInternacaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InternacaoDTO internacaoDTO = (InternacaoDTO) o;
        if (internacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternacaoDTO{" +
            "id=" + getId() +
            ", prioridade='" + getPrioridade() + "'" +
            ", justificativa='" + getJustificativa() + "'" +
            ", dataDaInternacao='" + getDataDaInternacao() + "'" +
            ", diferencaDeClasse='" + isDiferencaDeClasse() + "'" +
            ", solicitarProntuario='" + isSolicitarProntuario() + "'" +
            ", especialidade=" + getEspecialidadeId() +
            ", crm=" + getCrmId() +
            ", hospitalDeOrigem=" + getHospitalDeOrigemId() +
            ", origem=" + getOrigemId() +
            ", convenio=" + getConvenioId() +
            ", plano=" + getPlanoId() +
            ", procedimento=" + getProcedimentoId() +
            ", procedencia=" + getProcedenciaId() +
            ", modalidadeAssistencial=" + getModalidadeAssistencialId() +
            ", localDeAtendimento=" + getLocalDeAtendimentoId() +
            ", caraterDaInternacao=" + getCaraterDaInternacaoId() +
            "}";
    }
}
