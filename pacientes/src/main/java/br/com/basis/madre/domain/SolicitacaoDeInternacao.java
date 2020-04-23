package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.com.basis.madre.domain.enumeration.Prioridade;

/**
 * A SolicitacaoDeInternacao.
 */
@Entity
@Table(name = "solicitacao_de_internacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "solicitacaodeinternacao")
public class SolicitacaoDeInternacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_provavel_da_internacao", nullable = false)
    private LocalDate dataProvavelDaInternacao;

    @Column(name = "data_provavel_da_cirurgia")
    private LocalDate dataProvavelDaCirurgia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @NotNull
    @Column(name = "principais_sinais_e_sintomas_clinicos", nullable = false)
    private String principaisSinaisESintomasClinicos;

    @NotNull
    @Column(name = "condicoes_que_justificam_internacao", nullable = false)
    private String condicoesQueJustificamInternacao;

    @NotNull
    @Column(name = "principais_resultados_provas_diagnosticas", nullable = false)
    private String principaisResultadosProvasDiagnosticas;

    @ManyToOne
    @JsonIgnoreProperties("")
    private CID cidPrincipal;

    @ManyToOne
    @JsonIgnoreProperties("")
    private CID cidSecundario;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Equipe equipe;

    @ManyToOne
    @JsonIgnoreProperties("")
    private CRM crm;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Procedimento procedimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataProvavelDaInternacao() {
        return dataProvavelDaInternacao;
    }

    public SolicitacaoDeInternacao dataProvavelDaInternacao(LocalDate dataProvavelDaInternacao) {
        this.dataProvavelDaInternacao = dataProvavelDaInternacao;
        return this;
    }

    public void setDataProvavelDaInternacao(LocalDate dataProvavelDaInternacao) {
        this.dataProvavelDaInternacao = dataProvavelDaInternacao;
    }

    public LocalDate getDataProvavelDaCirurgia() {
        return dataProvavelDaCirurgia;
    }

    public SolicitacaoDeInternacao dataProvavelDaCirurgia(LocalDate dataProvavelDaCirurgia) {
        this.dataProvavelDaCirurgia = dataProvavelDaCirurgia;
        return this;
    }

    public void setDataProvavelDaCirurgia(LocalDate dataProvavelDaCirurgia) {
        this.dataProvavelDaCirurgia = dataProvavelDaCirurgia;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public SolicitacaoDeInternacao prioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getPrincipaisSinaisESintomasClinicos() {
        return principaisSinaisESintomasClinicos;
    }

    public SolicitacaoDeInternacao principaisSinaisESintomasClinicos(String principaisSinaisESintomasClinicos) {
        this.principaisSinaisESintomasClinicos = principaisSinaisESintomasClinicos;
        return this;
    }

    public void setPrincipaisSinaisESintomasClinicos(String principaisSinaisESintomasClinicos) {
        this.principaisSinaisESintomasClinicos = principaisSinaisESintomasClinicos;
    }

    public String getCondicoesQueJustificamInternacao() {
        return condicoesQueJustificamInternacao;
    }

    public SolicitacaoDeInternacao condicoesQueJustificamInternacao(String condicoesQueJustificamInternacao) {
        this.condicoesQueJustificamInternacao = condicoesQueJustificamInternacao;
        return this;
    }

    public void setCondicoesQueJustificamInternacao(String condicoesQueJustificamInternacao) {
        this.condicoesQueJustificamInternacao = condicoesQueJustificamInternacao;
    }

    public String getPrincipaisResultadosProvasDiagnosticas() {
        return principaisResultadosProvasDiagnosticas;
    }

    public SolicitacaoDeInternacao principaisResultadosProvasDiagnosticas(String principaisResultadosProvasDiagnosticas) {
        this.principaisResultadosProvasDiagnosticas = principaisResultadosProvasDiagnosticas;
        return this;
    }

    public void setPrincipaisResultadosProvasDiagnosticas(String principaisResultadosProvasDiagnosticas) {
        this.principaisResultadosProvasDiagnosticas = principaisResultadosProvasDiagnosticas;
    }

    public CID getCidPrincipal() {
        return cidPrincipal;
    }

    public SolicitacaoDeInternacao cidPrincipal(CID cID) {
        this.cidPrincipal = cID;
        return this;
    }

    public void setCidPrincipal(CID cID) {
        this.cidPrincipal = cID;
    }

    public CID getCidSecundario() {
        return cidSecundario;
    }

    public SolicitacaoDeInternacao cidSecundario(CID cID) {
        this.cidSecundario = cID;
        return this;
    }

    public void setCidSecundario(CID cID) {
        this.cidSecundario = cID;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public SolicitacaoDeInternacao equipe(Equipe equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public CRM getCrm() {
        return crm;
    }

    public SolicitacaoDeInternacao crm(CRM cRM) {
        this.crm = cRM;
        return this;
    }

    public void setCrm(CRM cRM) {
        this.crm = cRM;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public SolicitacaoDeInternacao procedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
        return this;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SolicitacaoDeInternacao solicitacaoDeInternacao = (SolicitacaoDeInternacao) o;
        if (solicitacaoDeInternacao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitacaoDeInternacao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitacaoDeInternacao{" +
            "id=" + getId() +
            ", dataProvavelDaInternacao='" + getDataProvavelDaInternacao() + "'" +
            ", dataProvavelDaCirurgia='" + getDataProvavelDaCirurgia() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", principaisSinaisESintomasClinicos='" + getPrincipaisSinaisESintomasClinicos() + "'" +
            ", condicoesQueJustificamInternacao='" + getCondicoesQueJustificamInternacao() + "'" +
            ", principaisResultadosProvasDiagnosticas='" + getPrincipaisResultadosProvasDiagnosticas() + "'" +
            "}";
    }
}
