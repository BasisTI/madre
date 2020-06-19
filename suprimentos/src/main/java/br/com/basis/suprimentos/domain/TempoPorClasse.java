package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import br.com.basis.suprimentos.domain.enumeration.TipoTempoPorClasse;

/**
 * A TempoPorClasse.
 */
@Entity
@Table(name = "tempo_por_classe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tempoporclasse")
public class TempoPorClasse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoTempoPorClasse tipo;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "quantidade_classe_a", nullable = false)
    private Integer quantidadeClasseA;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "quantidade_classe_b", nullable = false)
    private Integer quantidadeClasseB;

    @NotNull
    @Min(value = 0)
    @Max(value = 255)
    @Column(name = "quantidade_classe_c", nullable = false)
    private Integer quantidadeClasseC;

    @ManyToOne
    @JsonIgnoreProperties("tempoPorClasses")
    private Almoxarifado almoxarifado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTempoPorClasse getTipo() {
        return tipo;
    }

    public TempoPorClasse tipo(TipoTempoPorClasse tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoTempoPorClasse tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidadeClasseA() {
        return quantidadeClasseA;
    }

    public TempoPorClasse quantidadeClasseA(Integer quantidadeClasseA) {
        this.quantidadeClasseA = quantidadeClasseA;
        return this;
    }

    public void setQuantidadeClasseA(Integer quantidadeClasseA) {
        this.quantidadeClasseA = quantidadeClasseA;
    }

    public Integer getQuantidadeClasseB() {
        return quantidadeClasseB;
    }

    public TempoPorClasse quantidadeClasseB(Integer quantidadeClasseB) {
        this.quantidadeClasseB = quantidadeClasseB;
        return this;
    }

    public void setQuantidadeClasseB(Integer quantidadeClasseB) {
        this.quantidadeClasseB = quantidadeClasseB;
    }

    public Integer getQuantidadeClasseC() {
        return quantidadeClasseC;
    }

    public TempoPorClasse quantidadeClasseC(Integer quantidadeClasseC) {
        this.quantidadeClasseC = quantidadeClasseC;
        return this;
    }

    public void setQuantidadeClasseC(Integer quantidadeClasseC) {
        this.quantidadeClasseC = quantidadeClasseC;
    }

    public Almoxarifado getAlmoxarifado() {
        return almoxarifado;
    }

    public TempoPorClasse almoxarifado(Almoxarifado almoxarifado) {
        this.almoxarifado = almoxarifado;
        return this;
    }

    public void setAlmoxarifado(Almoxarifado almoxarifado) {
        this.almoxarifado = almoxarifado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TempoPorClasse)) {
            return false;
        }
        return id != null && id.equals(((TempoPorClasse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TempoPorClasse{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", quantidadeClasseA=" + getQuantidadeClasseA() +
            ", quantidadeClasseB=" + getQuantidadeClasseB() +
            ", quantidadeClasseC=" + getQuantidadeClasseC() +
            "}";
    }
}
