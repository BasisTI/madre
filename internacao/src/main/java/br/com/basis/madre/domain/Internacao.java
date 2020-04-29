package br.com.basis.madre.domain;

import br.com.basis.madre.domain.enumeration.Prioridade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
@NoArgsConstructor
@Entity
@Table(name = "internacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "internacao")
public class Internacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
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

    @ManyToMany
    @JoinTable(
        name = "internacao_cid",
        joinColumns = {@JoinColumn(name = "internacao_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "cid_id", referencedColumnName = "id")}
    )
    private Set<CID> cids = new HashSet<>();

    public Internacao prioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public Internacao justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public Internacao dataDaInternacao(LocalDate dataDaInternacao) {
        this.dataDaInternacao = dataDaInternacao;
        return this;
    }

    public Internacao diferencaDeClasse(Boolean diferencaDeClasse) {
        this.diferencaDeClasse = diferencaDeClasse;
        return this;
    }

    public Internacao solicitarProntuario(Boolean solicitarProntuario) {
        this.solicitarProntuario = solicitarProntuario;
        return this;
    }

}
