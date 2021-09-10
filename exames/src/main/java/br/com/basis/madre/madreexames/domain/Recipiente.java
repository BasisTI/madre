package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Recipiente.
 */
@Entity
@Table(name = "recipiente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "recipiente")
public class Recipiente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRecipiente")
    @SequenceGenerator(name = "seqRecipiente")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "anticoagulante", nullable = false)
    private Boolean anticoagulante;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

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

    public Recipiente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAnticoagulante() {
        return anticoagulante;
    }

    public Recipiente anticoagulante(Boolean anticoagulante) {
        this.anticoagulante = anticoagulante;
        return this;
    }

    public void setAnticoagulante(Boolean anticoagulante) {
        this.anticoagulante = anticoagulante;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Recipiente ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipiente)) {
            return false;
        }
        return id != null && id.equals(((Recipiente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recipiente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", anticoagulante='" + isAnticoagulante() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
