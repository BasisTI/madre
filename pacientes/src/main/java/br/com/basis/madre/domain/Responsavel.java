package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Responsavel.
 */
@Entity
@Table(name = "responsavel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "responsavel")
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "nome_do_responsavel")
    private String nomeDoResponsavel;


    //    @Field(type = FieldType.Nested)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "responsavel_telefone",
        joinColumns = {@JoinColumn(name = "responsavel_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "telefone_id", referencedColumnName = "id")}
    )
    private Set<Telefone> telefones = new HashSet<>();


    @Field(type = FieldType.Nested)
    @ManyToOne
    @JsonIgnoreProperties("responsavels")
    private GrauDeParentesco grauDeParentesco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoResponsavel() {
        return nomeDoResponsavel;
    }

    public Responsavel nomeDoResponsavel(String nomeDoResponsavel) {
        this.nomeDoResponsavel = nomeDoResponsavel;
        return this;
    }

    public void setNomeDoResponsavel(String nomeDoResponsavel) {
        this.nomeDoResponsavel = nomeDoResponsavel;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public GrauDeParentesco getGrauDeParentesco() {
        return grauDeParentesco;
    }

    public Responsavel grauDeParentesco(GrauDeParentesco grauDeParentesco) {
        this.grauDeParentesco = grauDeParentesco;
        return this;
    }

    public void setGrauDeParentesco(GrauDeParentesco grauDeParentesco) {
        this.grauDeParentesco = grauDeParentesco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsavel)) {
            return false;
        }
        return id != null && id.equals(((Responsavel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Responsavel{" +
            "id=" + getId() +
            ", nomeDoResponsavel='" + getNomeDoResponsavel() + "'" +
            "}";
    }
}
