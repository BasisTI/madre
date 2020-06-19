package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Lote.
 */
@Entity
@Table(name = "lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lote")
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descricao", length = 120, nullable = false)
    private String descricao;

    @Size(min = 1, max = 15)
    @Column(name = "serie", length = 15)
    private String serie;

    @Min(value = 0L)
    @Column(name = "quantidade_disponivel")
    private Long quantidadeDisponivel;

    @Min(value = 0L)
    @Column(name = "quantidade_bloqueada")
    private Long quantidadeBloqueada;

    @Min(value = 0L)
    @Column(name = "quantidade_problema")
    private Long quantidadeProblema;

    @NotNull
    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @ManyToOne
    @JsonIgnoreProperties("lotes")
    private MarcaComercial marcaComercial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lotes")
    private EstoqueAlmoxarifado estoque;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Lote descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSerie() {
        return serie;
    }

    public Lote serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Long getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public Lote quantidadeDisponivel(Long quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
        return this;
    }

    public void setQuantidadeDisponivel(Long quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Long getQuantidadeBloqueada() {
        return quantidadeBloqueada;
    }

    public Lote quantidadeBloqueada(Long quantidadeBloqueada) {
        this.quantidadeBloqueada = quantidadeBloqueada;
        return this;
    }

    public void setQuantidadeBloqueada(Long quantidadeBloqueada) {
        this.quantidadeBloqueada = quantidadeBloqueada;
    }

    public Long getQuantidadeProblema() {
        return quantidadeProblema;
    }

    public Lote quantidadeProblema(Long quantidadeProblema) {
        this.quantidadeProblema = quantidadeProblema;
        return this;
    }

    public void setQuantidadeProblema(Long quantidadeProblema) {
        this.quantidadeProblema = quantidadeProblema;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public Lote dataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
        return this;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public MarcaComercial getMarcaComercial() {
        return marcaComercial;
    }

    public Lote marcaComercial(MarcaComercial marcaComercial) {
        this.marcaComercial = marcaComercial;
        return this;
    }

    public void setMarcaComercial(MarcaComercial marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public EstoqueAlmoxarifado getEstoque() {
        return estoque;
    }

    public Lote estoque(EstoqueAlmoxarifado estoqueAlmoxarifado) {
        this.estoque = estoqueAlmoxarifado;
        return this;
    }

    public void setEstoque(EstoqueAlmoxarifado estoqueAlmoxarifado) {
        this.estoque = estoqueAlmoxarifado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lote)) {
            return false;
        }
        return id != null && id.equals(((Lote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Lote{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", serie='" + getSerie() + "'" +
            ", quantidadeDisponivel=" + getQuantidadeDisponivel() +
            ", quantidadeBloqueada=" + getQuantidadeBloqueada() +
            ", quantidadeProblema=" + getQuantidadeProblema() +
            ", dataValidade='" + getDataValidade() + "'" +
            "}";
    }
}
