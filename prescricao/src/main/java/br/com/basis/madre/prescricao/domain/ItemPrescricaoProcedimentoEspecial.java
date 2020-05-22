package br.com.basis.madre.prescricao.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;

/**
 * A ItemPrescricaoProcedimentoEspecial.
 */
@Entity
@Table(name = "item_prescricao_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "itemprescricaoprocedimentoespecial")
public class ItemPrescricaoProcedimentoEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * Tipo do procedimento especial
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_procedimento")
    private TipoProcedimentoEspecial tipoProcedimento;

    /**
     * Quanditade da Ortese ou prótese, valor deve ser um inteiro
     */
    @Min(value = 0)
    @Column(name = "quantidade_ortese_protese")
    private Integer quantidadeOrteseProtese;

    /**
     * Informações complementares para o procedimento
     */
    @Size(max = 255)
    @Column(name = "informacoes", length = 255)
    private String informacoes;

    /**
     * Justificativa para o procedimento especial
     */
    @Size(max = 255)
    @Column(name = "justificativa", length = 255)
    private String justificativa;

    /**
     * Duração do procedimento solicitado
     */
    @Min(value = 0)
    @Column(name = "duracao_solicitada")
    private Integer duracaoSolicitada;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private EspeciaisDiversos especiaisDiversos;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private CirurgiasLeito cirurgiasLeito;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private OrteseProtese orteseProtese;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoProcedimentoEspecials")
    private PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProcedimentoEspecial getTipoProcedimento() {
        return tipoProcedimento;
    }

    public ItemPrescricaoProcedimentoEspecial tipoProcedimento(TipoProcedimentoEspecial tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
        return this;
    }

    public void setTipoProcedimento(TipoProcedimentoEspecial tipoProcedimento) {
        this.tipoProcedimento = tipoProcedimento;
    }

    public Integer getQuantidadeOrteseProtese() {
        return quantidadeOrteseProtese;
    }

    public ItemPrescricaoProcedimentoEspecial quantidadeOrteseProtese(Integer quantidadeOrteseProtese) {
        this.quantidadeOrteseProtese = quantidadeOrteseProtese;
        return this;
    }

    public void setQuantidadeOrteseProtese(Integer quantidadeOrteseProtese) {
        this.quantidadeOrteseProtese = quantidadeOrteseProtese;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public ItemPrescricaoProcedimentoEspecial informacoes(String informacoes) {
        this.informacoes = informacoes;
        return this;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public ItemPrescricaoProcedimentoEspecial justificativa(String justificativa) {
        this.justificativa = justificativa;
        return this;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getDuracaoSolicitada() {
        return duracaoSolicitada;
    }

    public ItemPrescricaoProcedimentoEspecial duracaoSolicitada(Integer duracaoSolicitada) {
        this.duracaoSolicitada = duracaoSolicitada;
        return this;
    }

    public void setDuracaoSolicitada(Integer duracaoSolicitada) {
        this.duracaoSolicitada = duracaoSolicitada;
    }

    public EspeciaisDiversos getEspeciaisDiversos() {
        return especiaisDiversos;
    }

    public ItemPrescricaoProcedimentoEspecial especiaisDiversos(EspeciaisDiversos especiaisDiversos) {
        this.especiaisDiversos = especiaisDiversos;
        return this;
    }

    public void setEspeciaisDiversos(EspeciaisDiversos especiaisDiversos) {
        this.especiaisDiversos = especiaisDiversos;
    }

    public CirurgiasLeito getCirurgiasLeito() {
        return cirurgiasLeito;
    }

    public ItemPrescricaoProcedimentoEspecial cirurgiasLeito(CirurgiasLeito cirurgiasLeito) {
        this.cirurgiasLeito = cirurgiasLeito;
        return this;
    }

    public void setCirurgiasLeito(CirurgiasLeito cirurgiasLeito) {
        this.cirurgiasLeito = cirurgiasLeito;
    }

    public OrteseProtese getOrteseProtese() {
        return orteseProtese;
    }

    public ItemPrescricaoProcedimentoEspecial orteseProtese(OrteseProtese orteseProtese) {
        this.orteseProtese = orteseProtese;
        return this;
    }

    public void setOrteseProtese(OrteseProtese orteseProtese) {
        this.orteseProtese = orteseProtese;
    }

    public PrescricaoProcedimentoEspecial getPrescricaoProcedimentoEspecial() {
        return prescricaoProcedimentoEspecial;
    }

    public ItemPrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial(PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial) {
        this.prescricaoProcedimentoEspecial = prescricaoProcedimentoEspecial;
        return this;
    }

    public void setPrescricaoProcedimentoEspecial(PrescricaoProcedimentoEspecial prescricaoProcedimentoEspecial) {
        this.prescricaoProcedimentoEspecial = prescricaoProcedimentoEspecial;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPrescricaoProcedimentoEspecial)) {
            return false;
        }
        return id != null && id.equals(((ItemPrescricaoProcedimentoEspecial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemPrescricaoProcedimentoEspecial{" +
            "id=" + getId() +
            ", tipoProcedimento='" + getTipoProcedimento() + "'" +
            ", quantidadeOrteseProtese=" + getQuantidadeOrteseProtese() +
            ", informacoes='" + getInformacoes() + "'" +
            ", justificativa='" + getJustificativa() + "'" +
            ", duracaoSolicitada=" + getDuracaoSolicitada() +
            "}";
    }
}
