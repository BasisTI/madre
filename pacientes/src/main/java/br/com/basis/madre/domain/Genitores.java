package br.com.basis.madre.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Genitores.
 */
@Entity
@Table(name = "genitores")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "genitores")
public class Genitores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "prontuario_da_mae")
    private String prontuarioDaMae;

    @NotNull
    @Column(name = "nome_da_mae", nullable = false)
    private String nomeDaMae;

    @NotNull
    @Column(name = "nome_do_pai", nullable = false)
    private String nomeDoPai;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProntuarioDaMae() {
        return prontuarioDaMae;
    }

    public Genitores prontuarioDaMae(String prontuarioDaMae) {
        this.prontuarioDaMae = prontuarioDaMae;
        return this;
    }

    public void setProntuarioDaMae(String prontuarioDaMae) {
        this.prontuarioDaMae = prontuarioDaMae;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public Genitores nomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
        return this;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public Genitores nomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
        return this;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genitores)) {
            return false;
        }
        return id != null && id.equals(((Genitores) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Genitores{" +
            "id=" + getId() +
            ", prontuarioDaMae='" + getProntuarioDaMae() + "'" +
            ", nomeDaMae='" + getNomeDaMae() + "'" +
            ", nomeDoPai='" + getNomeDoPai() + "'" +
            "}";
    }
}
