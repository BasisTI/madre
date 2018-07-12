package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Acao.
 */
@Entity
@Table(name = "acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "acao")
public class Acao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nm_acao", length = 20, nullable = false)
    private String nm_acao;

    @NotNull
    @Size(max = 8)
    @Column(name = "cd_acao", length = 8, nullable = false)
    private String cd_acao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_acao() {
        return nm_acao;
    }

    public Acao nm_acao(String nm_acao) {
        this.nm_acao = nm_acao;
        return this;
    }

    public void setNm_acao(String nm_acao) {
        this.nm_acao = nm_acao;
    }

    public String getCd_acao() {
        return cd_acao;
    }

    public Acao cd_acao(String cd_acao) {
        this.cd_acao = cd_acao;
        return this;
    }

    public void setCd_acao(String cd_acao) {
        this.cd_acao = cd_acao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Acao acao = (Acao) o;
        if (acao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Acao{" +
            "id=" + getId() +
            ", nm_acao='" + getNm_acao() + "'" +
            ", cd_acao='" + getCd_acao() + "'" +
            "}";
    }
}
