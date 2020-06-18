package br.com.basis.suprimentos.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A TransferenciaAlmoxarifado.
 */
@Entity
@Table(name = "transferencia_almoxarifado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transferenciaalmoxarifado")
public class TransferenciaAlmoxarifado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transferenciaAlmoxarifados")
    private Almoxarifado origem;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transferenciaAlmoxarifados")
    private Almoxarifado destino;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public TransferenciaAlmoxarifado ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Almoxarifado getOrigem() {
        return origem;
    }

    public TransferenciaAlmoxarifado origem(Almoxarifado almoxarifado) {
        this.origem = almoxarifado;
        return this;
    }

    public void setOrigem(Almoxarifado almoxarifado) {
        this.origem = almoxarifado;
    }

    public Almoxarifado getDestino() {
        return destino;
    }

    public TransferenciaAlmoxarifado destino(Almoxarifado almoxarifado) {
        this.destino = almoxarifado;
        return this;
    }

    public void setDestino(Almoxarifado almoxarifado) {
        this.destino = almoxarifado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferenciaAlmoxarifado)) {
            return false;
        }
        return id != null && id.equals(((TransferenciaAlmoxarifado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransferenciaAlmoxarifado{" +
            "id=" + getId() +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
