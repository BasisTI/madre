package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A ItemNotaRecebimento.
 */
@Entity
@Table(name = "item_nota_recebimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "itemnotarecebimento")
public class ItemNotaRecebimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Min(value = 0L)
    @Max(value = 255L)
    @Column(name = "quantidade_receber", nullable = false)
    private Long quantidadeReceber;

    @NotNull
    @Size(max = 255)
    @Column(name = "quantidade_convertida", length = 255, nullable = false)
    private String quantidadeConvertida;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "valor_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @ManyToOne
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private Recebimento recebimento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private MarcaComercial marcaComercial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private Material material;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itemNotaRecebimentos")
    private UnidadeMedida unidadeMedida;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantidadeReceber() {
        return quantidadeReceber;
    }

    public ItemNotaRecebimento quantidadeReceber(Long quantidadeReceber) {
        this.quantidadeReceber = quantidadeReceber;
        return this;
    }

    public void setQuantidadeReceber(Long quantidadeReceber) {
        this.quantidadeReceber = quantidadeReceber;
    }

    public String getQuantidadeConvertida() {
        return quantidadeConvertida;
    }

    public ItemNotaRecebimento quantidadeConvertida(String quantidadeConvertida) {
        this.quantidadeConvertida = quantidadeConvertida;
        return this;
    }

    public void setQuantidadeConvertida(String quantidadeConvertida) {
        this.quantidadeConvertida = quantidadeConvertida;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public ItemNotaRecebimento valorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public ItemNotaRecebimento recebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
        return this;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public MarcaComercial getMarcaComercial() {
        return marcaComercial;
    }

    public ItemNotaRecebimento marcaComercial(MarcaComercial marcaComercial) {
        this.marcaComercial = marcaComercial;
        return this;
    }

    public void setMarcaComercial(MarcaComercial marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemNotaRecebimento material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public ItemNotaRecebimento unidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
        return this;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemNotaRecebimento)) {
            return false;
        }
        return id != null && id.equals(((ItemNotaRecebimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ItemNotaRecebimento{" +
            "id=" + getId() +
            ", quantidadeReceber=" + getQuantidadeReceber() +
            ", quantidadeConvertida='" + getQuantidadeConvertida() + "'" +
            ", valorTotal=" + getValorTotal() +
            "}";
    }
}
