package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Perfil.
 */
@Entity
@Table(name = "perfil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "perfil")
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nm_perfil")
    private Integer nmPerfil;

    @Column(name = "ds_perfil")
    private String dsPerfil;

    @Column(name = "st_excluido")
    private Boolean stExcluido;

    @Column(name = "st_ativo")
    private Boolean stAtivo;

    @NotNull
    @Column(name = "id_funcionalidade", nullable = false)
    private Integer idFuncionalidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNmPerfil() {
        return nmPerfil;
    }

    public Perfil nmPerfil(Integer nmPerfil) {
        this.nmPerfil = nmPerfil;
        return this;
    }

    public void setNmPerfil(Integer nmPerfil) {
        this.nmPerfil = nmPerfil;
    }

    public String getDsPerfil() {
        return dsPerfil;
    }

    public Perfil dsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
        return this;
    }

    public void setDsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
    }

    public Boolean isStExcluido() {
        return stExcluido;
    }

    public Perfil stExcluido(Boolean stExcluido) {
        this.stExcluido = stExcluido;
        return this;
    }

    public void setStExcluido(Boolean stExcluido) {
        this.stExcluido = stExcluido;
    }

    public Boolean isStAtivo() {
        return stAtivo;
    }

    public Perfil stAtivo(Boolean stAtivo) {
        this.stAtivo = stAtivo;
        return this;
    }

    public void setStAtivo(Boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

    public Integer getIdFuncionalidade() {
        return idFuncionalidade;
    }

    public Perfil idFuncionalidade(Integer idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
        return this;
    }

    public void setIdFuncionalidade(Integer idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
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
        Perfil perfil = (Perfil) o;
        if (perfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Perfil{" +
            "id=" + getId() +
            ", nmPerfil=" + getNmPerfil() +
            ", dsPerfil='" + getDsPerfil() + "'" +
            ", stExcluido='" + isStExcluido() + "'" +
            ", stAtivo='" + isStAtivo() + "'" +
            ", idFuncionalidade=" + getIdFuncionalidade() +
            "}";
    }
}
