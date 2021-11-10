package br.com.basis.madre.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import br.com.basis.madre.domain.enumeration.Prioridade;
import br.com.basis.madre.domain.enumeration.TipoDeAlta;
import lombok.Data;

@Data
@Entity
@Table(name = "internacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-internacao-internacao")
public class Internacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "id_paciente", nullable = false)
    private Long pacienteId;

    @NotNull
    @Column(name = "nome_paciente", nullable = false)
    private String pacienteNome;

    @NotNull
    @Column(name = "prontuario_paciente", nullable = false)
    private String pacienteProntuario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @NotNull
    @Column(name = "justificativa", nullable = false)
    private String justificativa;

    @NotNull
    @Column(name = "data_da_internacao", nullable = false)
    private ZonedDateTime dataDaInternacao;

    @Column(name = "diferenca_de_classe")
    private Boolean diferencaDeClasse;

    @Column(name = "solicitar_prontuario")
    private Boolean solicitarProntuario;

    @Column(name = "data_da_alta")
    private LocalDate dataDaAlta;

    @Column(name = "previsao_de_alta")
    private LocalDate previsaoDeAlta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_alta")
    private TipoDeAlta tipoDeAlta;

    @Column(name = "ativo")
    private Boolean ativo;

    @ManyToOne
    private Leito leito;

    @ManyToOne
    private Especialidade especialidade;

    @ManyToOne
    private CRM crm;

    @ManyToOne
    private Hospital hospitalDeOrigem;

    @ManyToOne
    private OrigemDaInternacao origem;

    @ManyToOne
    private ConvenioDeSaude convenio;

    @ManyToOne
    private PlanoDeSaude plano;

    @ManyToOne
    private Procedimento procedimento;

    @ManyToOne
    private Procedencia procedencia;

    @ManyToOne
    private ModalidadeAssistencial modalidadeAssistencial;

    @ManyToOne
    private LocalDeAtendimento localDeAtendimento;

    @ManyToOne
    private CaraterDaInternacao caraterDaInternacao;

    public Internacao prioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        return this;
    }


    public Internacao justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }


    public Internacao dataDaInternacao(ZonedDateTime dataDaInternacao) {
        this.dataDaInternacao = dataDaInternacao;
        return this;
    }

    public Boolean isDiferencaDeClasse() {
        return diferencaDeClasse;
    }

    public Internacao diferencaDeClasse(Boolean diferencaDeClasse) {
        this.diferencaDeClasse = diferencaDeClasse;
        return this;
    }

    public Boolean isSolicitarProntuario() {
        return solicitarProntuario;
    }

    public Internacao solicitarProntuario(Boolean solicitarProntuario) {
        this.solicitarProntuario = solicitarProntuario;
        return this;
    }

    public Internacao especialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    public Internacao crm(CRM cRM) {
        this.crm = cRM;
        return this;
    }

    public Internacao hospitalDeOrigem(Hospital hospital) {
        this.hospitalDeOrigem = hospital;
        return this;
    }

    public Internacao origem(OrigemDaInternacao origemDaInternacao) {
        this.origem = origemDaInternacao;
        return this;
    }

    public Internacao convenio(ConvenioDeSaude convenioDeSaude) {
        this.convenio = convenioDeSaude;
        return this;
    }

    public Internacao plano(PlanoDeSaude planoDeSaude) {
        this.plano = planoDeSaude;
        return this;
    }

    public Internacao procedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
        return this;
    }

    public Internacao procedencia(Procedencia procedencia) {
        this.procedencia = procedencia;
        return this;
    }

    public Internacao modalidadeAssistencial(ModalidadeAssistencial modalidadeAssistencial) {
        this.modalidadeAssistencial = modalidadeAssistencial;
        return this;
    }

    public Internacao localDeAtendimento(LocalDeAtendimento localDeAtendimento) {
        this.localDeAtendimento = localDeAtendimento;
        return this;
    }

    public Internacao caraterDaInternacao(CaraterDaInternacao caraterDaInternacao) {
        this.caraterDaInternacao = caraterDaInternacao;
        return this;
    }

    public Internacao ativo(Boolean ativo){
        this.ativo = ativo;
        return this;
    }

    public Internacao dataDaAlta(LocalDate dataDaAlta){
        this.dataDaAlta = dataDaAlta;
        return this;
    }

    public Internacao previsaoDeAlta(LocalDate previsaoDeAlta){
        this.previsaoDeAlta = previsaoDeAlta;
        return this;
    }

    public Internacao tipoDeAlta(TipoDeAlta tipoDeAlta){
        this.tipoDeAlta = tipoDeAlta;
        return this;
    }
    public Internacao pacienteNome(String pacienteNome){
        this.pacienteNome = pacienteNome;
        return this;
    }
    public Internacao pacienteProntuario(String pacienteProntuario) {
        this.pacienteProntuario = pacienteProntuario;
        return this;
    }

}
