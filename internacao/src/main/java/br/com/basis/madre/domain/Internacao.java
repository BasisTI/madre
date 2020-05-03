package br.com.basis.madre.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import br.com.basis.madre.domain.enumeration.Prioridade;

/**
 * A Internacao.
 */
@Entity
@Table(name = "internacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "internacao")
public class Internacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @NotNull
    @Column(name = "justificativa", nullable = false)
    private String justificativa;

    @NotNull
    @Column(name = "data_da_internacao", nullable = false)
    private LocalDate dataDaInternacao;

    @Column(name = "diferenca_de_classe")
    private Boolean diferencaDeClasse;

    @Column(name = "solicitar_prontuario")
    private Boolean solicitarProntuario;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private Especialidade especialidade;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private CRM crm;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private Hospital hospitalDeOrigem;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private OrigemDaInternacao origem;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private ConvenioDeSaude convenio;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private PlanoDeSaude plano;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private Procedimento procedimento;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private Procedencia procedencia;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private ModalidadeAssistencial modalidadeAssistencial;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private LocalDeAtendimento localDeAtendimento;

    @ManyToOne
    @JsonIgnoreProperties("internacaos")
    private CaraterDaInternacao caraterDaInternacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Internacao prioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public Internacao justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public LocalDate getDataDaInternacao() {
        return dataDaInternacao;
    }

    public Internacao dataDaInternacao(LocalDate dataDaInternacao) {
        this.dataDaInternacao = dataDaInternacao;
        return this;
    }

    public void setDataDaInternacao(LocalDate dataDaInternacao) {
        this.dataDaInternacao = dataDaInternacao;
    }

    public Boolean isDiferencaDeClasse() {
        return diferencaDeClasse;
    }

    public Internacao diferencaDeClasse(Boolean diferencaDeClasse) {
        this.diferencaDeClasse = diferencaDeClasse;
        return this;
    }

    public void setDiferencaDeClasse(Boolean diferencaDeClasse) {
        this.diferencaDeClasse = diferencaDeClasse;
    }

    public Boolean isSolicitarProntuario() {
        return solicitarProntuario;
    }

    public Internacao solicitarProntuario(Boolean solicitarProntuario) {
        this.solicitarProntuario = solicitarProntuario;
        return this;
    }

    public void setSolicitarProntuario(Boolean solicitarProntuario) {
        this.solicitarProntuario = solicitarProntuario;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Internacao especialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public CRM getCrm() {
        return crm;
    }

    public Internacao crm(CRM cRM) {
        this.crm = cRM;
        return this;
    }

    public void setCrm(CRM cRM) {
        this.crm = cRM;
    }

    public Hospital getHospitalDeOrigem() {
        return hospitalDeOrigem;
    }

    public Internacao hospitalDeOrigem(Hospital hospital) {
        this.hospitalDeOrigem = hospital;
        return this;
    }

    public void setHospitalDeOrigem(Hospital hospital) {
        this.hospitalDeOrigem = hospital;
    }

    public OrigemDaInternacao getOrigem() {
        return origem;
    }

    public Internacao origem(OrigemDaInternacao origemDaInternacao) {
        this.origem = origemDaInternacao;
        return this;
    }

    public void setOrigem(OrigemDaInternacao origemDaInternacao) {
        this.origem = origemDaInternacao;
    }

    public ConvenioDeSaude getConvenio() {
        return convenio;
    }

    public Internacao convenio(ConvenioDeSaude convenioDeSaude) {
        this.convenio = convenioDeSaude;
        return this;
    }

    public void setConvenio(ConvenioDeSaude convenioDeSaude) {
        this.convenio = convenioDeSaude;
    }

    public PlanoDeSaude getPlano() {
        return plano;
    }

    public Internacao plano(PlanoDeSaude planoDeSaude) {
        this.plano = planoDeSaude;
        return this;
    }

    public void setPlano(PlanoDeSaude planoDeSaude) {
        this.plano = planoDeSaude;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Internacao procedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
        return this;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Procedencia getProcedencia() {
        return procedencia;
    }

    public Internacao procedencia(Procedencia procedencia) {
        this.procedencia = procedencia;
        return this;
    }

    public void setProcedencia(Procedencia procedencia) {
        this.procedencia = procedencia;
    }

    public ModalidadeAssistencial getModalidadeAssistencial() {
        return modalidadeAssistencial;
    }

    public Internacao modalidadeAssistencial(ModalidadeAssistencial modalidadeAssistencial) {
        this.modalidadeAssistencial = modalidadeAssistencial;
        return this;
    }

    public void setModalidadeAssistencial(ModalidadeAssistencial modalidadeAssistencial) {
        this.modalidadeAssistencial = modalidadeAssistencial;
    }

    public LocalDeAtendimento getLocalDeAtendimento() {
        return localDeAtendimento;
    }

    public Internacao localDeAtendimento(LocalDeAtendimento localDeAtendimento) {
        this.localDeAtendimento = localDeAtendimento;
        return this;
    }

    public void setLocalDeAtendimento(LocalDeAtendimento localDeAtendimento) {
        this.localDeAtendimento = localDeAtendimento;
    }

    public CaraterDaInternacao getCaraterDaInternacao() {
        return caraterDaInternacao;
    }

    public Internacao caraterDaInternacao(CaraterDaInternacao caraterDaInternacao) {
        this.caraterDaInternacao = caraterDaInternacao;
        return this;
    }

    public void setCaraterDaInternacao(CaraterDaInternacao caraterDaInternacao) {
        this.caraterDaInternacao = caraterDaInternacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Internacao)) {
            return false;
        }
        return id != null && id.equals(((Internacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Internacao{" +
            "id=" + getId() +
            ", prioridade='" + getPrioridade() + "'" +
            ", justificativa='" + getJustificativa() + "'" +
            ", dataDaInternacao='" + getDataDaInternacao() + "'" +
            ", diferencaDeClasse='" + isDiferencaDeClasse() + "'" +
            ", solicitarProntuario='" + isSolicitarProntuario() + "'" +
            "}";
    }
}
