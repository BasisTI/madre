package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.Prioridade;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "solicitacao_de_internacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-internacao-solicitacaodeinternacao")
public class SolicitacaoDeInternacao implements Serializable {

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
    private CID cidPrincipal;

    @ManyToOne
    private CID cidSecundario;

    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private CRM crm;

    @ManyToOne
    private Procedimento procedimento;

    public SolicitacaoDeInternacao dataProvavelDaInternacao(LocalDate dataProvavelDaInternacao) {
        this.dataProvavelDaInternacao = dataProvavelDaInternacao;
        return this;
    }

    public SolicitacaoDeInternacao dataProvavelDaCirurgia(LocalDate dataProvavelDaCirurgia) {
        this.dataProvavelDaCirurgia = dataProvavelDaCirurgia;
        return this;
    }

    public SolicitacaoDeInternacao prioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public SolicitacaoDeInternacao principaisSinaisESintomasClinicos(String principaisSinaisESintomasClinicos) {
        this.principaisSinaisESintomasClinicos = principaisSinaisESintomasClinicos;
        return this;
    }

    public SolicitacaoDeInternacao condicoesQueJustificamInternacao(String condicoesQueJustificamInternacao) {
        this.condicoesQueJustificamInternacao = condicoesQueJustificamInternacao;
        return this;
    }

    public SolicitacaoDeInternacao principaisResultadosProvasDiagnosticas(String principaisResultadosProvasDiagnosticas) {
        this.principaisResultadosProvasDiagnosticas = principaisResultadosProvasDiagnosticas;
        return this;
    }
}
