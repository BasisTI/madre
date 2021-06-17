package br.com.basis.madre.exames.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Sinonimo.
 */
@Entity
@Table(name = "sinonimo")
public class Sinonimo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSinonimo")
    @SequenceGenerator(name = "seqSinonimo")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "situacao")
    private Boolean situacao;

    @ManyToOne
    @JsonIgnoreProperties(value = "sinonimos", allowSetters = true)
    private Exame sinonimo;

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

    public Sinonimo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public Sinonimo situacao(Boolean situacao) {
        this.situacao = situacao;
        return this;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public Exame getSinonimo() {
        return sinonimo;
    }

    public Sinonimo sinonimo(Exame exame) {
        this.sinonimo = exame;
        return this;
    }

    public void setSinonimo(Exame exame) {
        this.sinonimo = exame;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sinonimo)) {
            return false;
        }
        return id != null && id.equals(((Sinonimo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sinonimo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", situacao='" + isSituacao() + "'" +
            "}";
    }
}
