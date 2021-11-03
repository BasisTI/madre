package br.com.basis.madre.seguranca.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-seguranca-municipio")
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMunicipio")
    @SequenceGenerator(name = "seqMunicipio")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "nome_do_distrito")
    private String nomeDoDistrito;

    @NotNull
    @Column(name = "ibge", nullable = false)
    private String ibge;

    @ManyToOne
    @JsonIgnoreProperties(value = "municipios", allowSetters = true)
    private UF uf;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Municipio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeDoDistrito() {
        return nomeDoDistrito;
    }

    public Municipio nomeDoDistrito(String nomeDoDistrito) {
        this.nomeDoDistrito = nomeDoDistrito;
        return this;
    }

    public void setNomeDoDistrito(String nomeDoDistrito) {
        this.nomeDoDistrito = nomeDoDistrito;
    }

    public String getIbge() {
        return ibge;
    }

    public Municipio ibge(String ibge) {
        this.ibge = ibge;
        return this;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public UF getUf() {
        return uf;
    }

    public Municipio uf(UF uF) {
        this.uf = uF;
        return this;
    }

    public void setUf(UF uF) {
        this.uf = uF;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipio)) {
            return false;
        }
        return id != null && id.equals(((Municipio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeDoDistrito='" + getNomeDoDistrito() + "'" +
            ", ibge='" + getIbge() + "'" +
            "}";
    }
}
