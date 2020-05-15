package br.com.basis.madre.prescricao.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ItemPrescricaoDieta.
 */
@Entity
@Table(name = "item_prescricao_dieta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "itemprescricaodieta")
public class ItemPrescricaoDieta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "quantidade", precision = 21, scale = 2)
    private BigDecimal quantidade;

    @Min(value = 0)
    @Column(name = "frequencia")
    private Integer frequencia;

    @Column(name = "numero_vezes")
    private Integer numeroVezes;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoItemDieta tipoItemDieta;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoAprazamento tipoAprazamento;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private TipoUnidadeDieta tipoUnidadeDieta;

    @ManyToOne
    @JsonIgnoreProperties("itemPrescricaoDietas")
    private PrescricaoDieta prescricaoDieta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public ItemPrescricaoDieta quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public ItemPrescricaoDieta frequencia(Integer frequencia) {
        this.frequencia = frequencia;
        return this;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public Integer getNumeroVezes() {
        return numeroVezes;
    }

    public ItemPrescricaoDieta numeroVezes(Integer numeroVezes) {
        this.numeroVezes = numeroVezes;
        return this;
    }

    public void setNumeroVezes(Integer numeroVezes) {
        this.numeroVezes = numeroVezes;
    }

    public TipoItemDieta getTipoItemDieta() {
        return tipoItemDieta;
    }

    public ItemPrescricaoDieta tipoItemDieta(TipoItemDieta tipoItemDieta) {
        this.tipoItemDieta = tipoItemDieta;
        return this;
    }

    public void setTipoItemDieta(TipoItemDieta tipoItemDieta) {
        this.tipoItemDieta = tipoItemDieta;
    }

    public TipoAprazamento getTipoAprazamento() {
        return tipoAprazamento;
    }

    public ItemPrescricaoDieta tipoAprazamento(TipoAprazamento tipoAprazamento) {
        this.tipoAprazamento = tipoAprazamento;
        return this;
    }

    public void setTipoAprazamento(TipoAprazamento tipoAprazamento) {
        this.tipoAprazamento = tipoAprazamento;
    }

    public TipoUnidadeDieta getTipoUnidadeDieta() {
        return tipoUnidadeDieta;
    }

    public ItemPrescricaoDieta tipoUnidadeDieta(TipoUnidadeDieta tipoUnidadeDieta) {
        this.tipoUnidadeDieta = tipoUnidadeDieta;
        return this;
    }

    public void setTipoUnidadeDieta(TipoUnidadeDieta tipoUnidadeDieta) {
        this.tipoUnidadeDieta = tipoUnidadeDieta;
    }

    public PrescricaoDieta getPrescricaoDieta() {
        return prescricaoDieta;
    }

    public ItemPrescricaoDieta prescricaoDieta(PrescricaoDieta prescricaoDieta) {
        this.prescricaoDieta = prescricaoDieta;
        return this;
    }

    public void setPrescricaoDieta(PrescricaoDieta prescricaoDieta) {
        this.prescricaoDieta = prescricaoDieta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPrescricaoDieta)) {
            return false;
        }
        return id != null && id.equals(((ItemPrescricaoDieta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemPrescricaoDieta{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", frequencia=" + getFrequencia() +
            ", numeroVezes=" + getNumeroVezes() +
            "}";
    }
}
