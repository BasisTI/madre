package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-pacientes-municipio")
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Field(type = FieldType.Text)
    @Column(name = "nome_do_distrito")
    private String nomeDoDistrito;


    @Field(type = FieldType.Text)
    @NotNull
    @Column(name = "ibge", nullable = false)
    private String ibge;

    @Field(type = FieldType.Object)
    @ManyToOne
    @JsonIgnoreProperties("municipios")
    private UF uf;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
