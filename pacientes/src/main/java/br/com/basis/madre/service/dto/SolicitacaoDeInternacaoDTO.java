package br.com.basis.madre.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.basis.madre.domain.enumeration.Prioridade;

/**
 * A DTO for the SolicitacaoDeInternacao entity.
 */
public class SolicitacaoDeInternacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataProvavelDaInternacao;

    private LocalDate dataProvavelDaCirurgia;

    @NotNull
    private Prioridade prioridade;

    @NotNull
    private String principaisSinaisESintomasClinicos;

    @NotNull
    private String condicoesQueJustificamInternacao;

    @NotNull
    private String principaisResultadosProvasDiagnosticas;

    private Long cidPrincipalId;

    private Long cidSecundarioId;

    private Long equipeId;

    private Long crmId;

    private Long procedimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataProvavelDaInternacao() {
        return dataProvavelDaInternacao;
    }

    public void setDataProvavelDaInternacao(LocalDate dataProvavelDaInternacao) {
        this.dataProvavelDaInternacao = dataProvavelDaInternacao;
    }

    public LocalDate getDataProvavelDaCirurgia() {
        return dataProvavelDaCirurgia;
    }

    public void setDataProvavelDaCirurgia(LocalDate dataProvavelDaCirurgia) {
        this.dataProvavelDaCirurgia = dataProvavelDaCirurgia;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getPrincipaisSinaisESintomasClinicos() {
        return principaisSinaisESintomasClinicos;
    }

    public void setPrincipaisSinaisESintomasClinicos(String principaisSinaisESintomasClinicos) {
        this.principaisSinaisESintomasClinicos = principaisSinaisESintomasClinicos;
    }

    public String getCondicoesQueJustificamInternacao() {
        return condicoesQueJustificamInternacao;
    }

    public void setCondicoesQueJustificamInternacao(String condicoesQueJustificamInternacao) {
        this.condicoesQueJustificamInternacao = condicoesQueJustificamInternacao;
    }

    public String getPrincipaisResultadosProvasDiagnosticas() {
        return principaisResultadosProvasDiagnosticas;
    }

    public void setPrincipaisResultadosProvasDiagnosticas(String principaisResultadosProvasDiagnosticas) {
        this.principaisResultadosProvasDiagnosticas = principaisResultadosProvasDiagnosticas;
    }

    public Long getCidPrincipalId() {
        return cidPrincipalId;
    }

    public void setCidPrincipalId(Long cIDId) {
        this.cidPrincipalId = cIDId;
    }

    public Long getCidSecundarioId() {
        return cidSecundarioId;
    }

    public void setCidSecundarioId(Long cIDId) {
        this.cidSecundarioId = cIDId;
    }

    public Long getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(Long equipeId) {
        this.equipeId = equipeId;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long cRMId) {
        this.crmId = cRMId;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO = (SolicitacaoDeInternacaoDTO) o;
        if (solicitacaoDeInternacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitacaoDeInternacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitacaoDeInternacaoDTO{" +
            "id=" + getId() +
            ", dataProvavelDaInternacao='" + getDataProvavelDaInternacao() + "'" +
            ", dataProvavelDaCirurgia='" + getDataProvavelDaCirurgia() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", principaisSinaisESintomasClinicos='" + getPrincipaisSinaisESintomasClinicos() + "'" +
            ", condicoesQueJustificamInternacao='" + getCondicoesQueJustificamInternacao() + "'" +
            ", principaisResultadosProvasDiagnosticas='" + getPrincipaisResultadosProvasDiagnosticas() + "'" +
            ", cidPrincipal=" + getCidPrincipalId() +
            ", cidSecundario=" + getCidSecundarioId() +
            ", equipe=" + getEquipeId() +
            ", crm=" + getCrmId() +
            ", procedimento=" + getProcedimentoId() +
            "}";
    }
}
