package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Caracteristica.
 */
@Entity
@Table(name = "caracteristica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "caracteristica")
public class Caracteristica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "caracteristicas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Unidade> unidades = new HashSet<>();

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

    public Caracteristica nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Unidade> getUnidades() {
        return unidades;
    }

    public Caracteristica unidades(Set<Unidade> unidades) {
        this.unidades = unidades;
        return this;
    }

    public Caracteristica addUnidade(Unidade unidade) {
        this.unidades.add(unidade);
        unidade.getCaracteristicas().add(this);
        return this;
    }

    public Caracteristica removeUnidade(Unidade unidade) {
        this.unidades.remove(unidade);
        unidade.getCaracteristicas().remove(this);
        return this;
    }

    public void setUnidades(Set<Unidade> unidades) {
        this.unidades = unidades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Caracteristica)) {
            return false;
        }
        return id != null && id.equals(((Caracteristica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Caracteristica{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
