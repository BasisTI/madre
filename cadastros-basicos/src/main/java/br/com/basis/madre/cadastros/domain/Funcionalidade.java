package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Funcionalidade.
 */
@Entity
@Table(name = "funcionalidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "funcionalidade")
public class Funcionalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nm_funcionalidade", length = 20, nullable = false)
    private String nm_funcionalidade;

    @NotNull
    @Size(max = 20)
    @Column(name = "cd_funcionalidade", length = 20, nullable = false)
    private String cd_funcionalidade;

    @Column(name = "dh_cadastro")
    private LocalDate dh_cadastro;

    @NotNull
    @Size(max = 1)
    @Column(name = "st_excluido", length = 1, nullable = false)
    private String st_excluido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_funcionalidade() {
        return nm_funcionalidade;
    }

    public Funcionalidade nm_funcionalidade(String nm_funcionalidade) {
        this.nm_funcionalidade = nm_funcionalidade;
        return this;
    }

    public void setNm_funcionalidade(String nm_funcionalidade) {
        this.nm_funcionalidade = nm_funcionalidade;
    }

    public String getCd_funcionalidade() {
        return cd_funcionalidade;
    }

    public Funcionalidade cd_funcionalidade(String cd_funcionalidade) {
        this.cd_funcionalidade = cd_funcionalidade;
        return this;
    }

    public void setCd_funcionalidade(String cd_funcionalidade) {
        this.cd_funcionalidade = cd_funcionalidade;
    }

    public LocalDate getDh_cadastro() {
        return dh_cadastro;
    }

    public Funcionalidade dh_cadastro(LocalDate dh_cadastro) {
        this.dh_cadastro = dh_cadastro;
        return this;
    }

    public void setDh_cadastro(LocalDate dh_cadastro) {
        this.dh_cadastro = dh_cadastro;
    }

    public String getSt_excluido() {
        return st_excluido;
    }

    public Funcionalidade st_excluido(String st_excluido) {
        this.st_excluido = st_excluido;
        return this;
    }

    public void setSt_excluido(String st_excluido) {
        this.st_excluido = st_excluido;
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
        Funcionalidade funcionalidade = (Funcionalidade) o;
        if (funcionalidade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionalidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Funcionalidade{" +
            "id=" + getId() +
            ", nm_funcionalidade='" + getNm_funcionalidade() + "'" +
            ", cd_funcionalidade='" + getCd_funcionalidade() + "'" +
            ", dh_cadastro='" + getDh_cadastro() + "'" +
            ", st_excluido='" + getSt_excluido() + "'" +
            "}";
    }
}
