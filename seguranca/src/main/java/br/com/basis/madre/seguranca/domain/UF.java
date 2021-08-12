package br.com.basis.madre.seguranca.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A UF.
 */
@Entity
@Table(name = "uf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "uf")
public class UF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUF")
    @SequenceGenerator(name = "seqUF")
    private Long id;

    @Column(name = "unidade_federativa")
    private String unidadeFederativa;

    @Column(name = "sigla")
    private String sigla;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnidadeFederativa() {
        return unidadeFederativa;
    }

    public UF unidadeFederativa(String unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
        return this;
    }

    public void setUnidadeFederativa(String unidadeFederativa) {
        this.unidadeFederativa = unidadeFederativa;
    }

    public String getSigla() {
        return sigla;
    }

    public UF sigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UF)) {
            return false;
        }
        return id != null && id.equals(((UF) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UF{" +
            "id=" + getId() +
            ", unidadeFederativa='" + getUnidadeFederativa() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
