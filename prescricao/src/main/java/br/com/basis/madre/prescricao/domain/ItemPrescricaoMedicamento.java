package br.com.basis.madre.prescricao.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.basis.madre.prescricao.domain.enumeration.UnidadeTempo;

/**
 * A ItemPrescricaoMedicamento.
 */
@Entity
@Table(name = "item_prescricao_medicamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "itemprescricaomedicamento")
public class ItemPrescricaoMedicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "id_medicamento", nullable = false)
    private Long idMedicamento;
    
    @NotNull
    private Long idListaMedicamentos;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "dose", precision = 21, scale = 2, nullable = false)
    private BigDecimal dose;

    @Min(value = 0)
    @Column(name = "frequencia")
    private Integer frequencia;

    @Column(name = "todas_vias")
    private Boolean todasVias;

    @Column(name = "bomba_infusao")
    private Boolean bombaInfusao;

    @DecimalMin(value = "0")
    @Column(name = "velocidade_infusao", precision = 21, scale = 2)
    private BigDecimal velocidadeInfusao;

    @Min(value = 0)
    @Column(name = "tempo_infusao")
    private Integer tempoInfusao;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_tempo")
    private UnidadeTempo unidadeTempo;

    @Column(name = "inicio_administracao")
    private LocalDate inicioAdministracao;

    @Column(name = "condicao_necessaria")
    private Boolean condicaoNecessaria;

    @Size(max = 255)
    @Column(name = "observacao_condicao", length = 255)
    private String observacaoCondicao;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoMedicamentos")
    private ViasAdministracao viasAdministracao;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoMedicamentos")
    private Diluente diluente;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoMedicamentos")
    private UnidadeInfusao unidadeInfusao;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoMedicamentos")
    private UnidadeDose unidadeDose;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoMedicamentos")
    private PrescricaoMedicamento prescricaoMedicamento;
    
    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoMedicamentos")
    private TipoAprazamento tipoAprazamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMedicamento() {
        return idMedicamento;
    }

    public ItemPrescricaoMedicamento idMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
        return this;
    }

    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public ItemPrescricaoMedicamento dose(BigDecimal dose) {
        this.dose = dose;
        return this;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public ItemPrescricaoMedicamento frequencia(Integer frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Boolean isTodasVias() {
        return todasVias;
    }

    public ItemPrescricaoMedicamento todasVias(Boolean todasVias) {
        this.todasVias = todasVias;
        return this;
    }

    public void setTodasVias(Boolean todasVias) {
        this.todasVias = todasVias;
    }

    public Boolean isBombaInfusao() {
        return bombaInfusao;
    }

    public ItemPrescricaoMedicamento bombaInfusao(Boolean bombaInfusao) {
        this.bombaInfusao = bombaInfusao;
        return this;
    }

    public void setBombaInfusao(Boolean bombaInfusao) {
        this.bombaInfusao = bombaInfusao;
    }

    public BigDecimal getVelocidadeInfusao() {
        return velocidadeInfusao;
    }

    public ItemPrescricaoMedicamento velocidadeInfusao(BigDecimal velocidadeInfusao) {
        this.velocidadeInfusao = velocidadeInfusao;
        return this;
    }

    public void setVelocidadeInfusao(BigDecimal velocidadeInfusao) {
        this.velocidadeInfusao = velocidadeInfusao;
    }

    public Integer getTempoInfusao() {
        return tempoInfusao;
    }

    public ItemPrescricaoMedicamento tempoInfusao(Integer tempoInfusao) {
        this.tempoInfusao = tempoInfusao;
        return this;
    }

    public void setTempoInfusao(Integer tempoInfusao) {
        this.tempoInfusao = tempoInfusao;
    }

    public UnidadeTempo getUnidadeTempo() {
        return unidadeTempo;
    }

    public ItemPrescricaoMedicamento unidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
        return this;
    }

    public void setUnidadeTempo(UnidadeTempo unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
    }

    public LocalDate getInicioAdministracao() {
        return inicioAdministracao;
    }

    public ItemPrescricaoMedicamento inicioAdministracao(LocalDate inicioAdministracao) {
        this.inicioAdministracao = inicioAdministracao;
        return this;
    }

    public void setInicioAdministracao(LocalDate inicioAdministracao) {
        this.inicioAdministracao = inicioAdministracao;
    }

    public Boolean isCondicaoNecessaria() {
        return condicaoNecessaria;
    }

    public ItemPrescricaoMedicamento condicaoNecessaria(Boolean condicaoNecessaria) {
        this.condicaoNecessaria = condicaoNecessaria;
        return this;
    }

    public void setCondicaoNecessaria(Boolean condicaoNecessaria) {
        this.condicaoNecessaria = condicaoNecessaria;
    }

    public String getObservacaoCondicao() {
        return observacaoCondicao;
    }

    public ItemPrescricaoMedicamento observacaoCondicao(String observacaoCondicao) {
        this.observacaoCondicao = observacaoCondicao;
        return this;
    }

    public void setObservacaoCondicao(String observacaoCondicao) {
        this.observacaoCondicao = observacaoCondicao;
    }

    public ViasAdministracao getViasAdministracao() {
        return viasAdministracao;
    }

    public ItemPrescricaoMedicamento viasAdministracao(ViasAdministracao viasAdministracao) {
        this.viasAdministracao = viasAdministracao;
        return this;
    }

    public void setViasAdministracao(ViasAdministracao viasAdministracao) {
        this.viasAdministracao = viasAdministracao;
    }

    public Diluente getDiluente() {
        return diluente;
    }

    public ItemPrescricaoMedicamento diluente(Diluente diluente) {
        this.diluente = diluente;
        return this;
    }

    public void setDiluente(Diluente diluente) {
        this.diluente = diluente;
    }

    public UnidadeInfusao getUnidadeInfusao() {
        return unidadeInfusao;
    }

    public ItemPrescricaoMedicamento unidadeInfusao(UnidadeInfusao unidadeInfusao) {
        this.unidadeInfusao = unidadeInfusao;
        return this;
    }

    public void setUnidadeInfusao(UnidadeInfusao unidadeInfusao) {
        this.unidadeInfusao = unidadeInfusao;
    }

    public UnidadeDose getUnidadeDose() {
        return unidadeDose;
    }

    public ItemPrescricaoMedicamento unidadeDose(UnidadeDose unidadeDose) {
        this.unidadeDose = unidadeDose;
        return this;
    }

    public void setUnidadeDose(UnidadeDose unidadeDose) {
        this.unidadeDose = unidadeDose;
    }

    public PrescricaoMedicamento getPrescricaoMedicamento() {
        return prescricaoMedicamento;
    }

    public ItemPrescricaoMedicamento prescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento) {
        this.prescricaoMedicamento = prescricaoMedicamento;
        return this;
    }

    public void setPrescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento) {
        this.prescricaoMedicamento = prescricaoMedicamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPrescricaoMedicamento)) {
            return false;
        }
        return id != null && id.equals(((ItemPrescricaoMedicamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemPrescricaoMedicamento{" +
            "id=" + getId() +
            ", idMedicamento=" + getIdMedicamento() +
            ", dose=" + getDose() +
            ", frequencia=" + getFrequencia() +
            ", todasVias='" + isTodasVias() + "'" +
            ", bombaInfusao='" + isBombaInfusao() + "'" +
            ", velocidadeInfusao=" + getVelocidadeInfusao() +
            ", tempoInfusao=" + getTempoInfusao() +
            ", unidadeTempo='" + getUnidadeTempo() + "'" +
            ", inicioAdministracao='" + getInicioAdministracao() + "'" +
            ", condicaoNecessaria='" + isCondicaoNecessaria() + "'" +
            ", observacaoCondicao='" + getObservacaoCondicao() + "'" +
            "}";
    }
}
