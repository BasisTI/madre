package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

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
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nm_funcionalidade", length = 20, nullable = false)
    private String nmFuncionalidade;

    @NotNull
    @Size(max = 20)
    @Column(name = "cd_funcionalidade", length = 20, nullable = false)
    private String cdFuncionalidade;

    @NotNull
    @Size(max = 1)
    @Column(name = "st_excluido", length = 1, nullable = false)
    private String stExcluido;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "funcionalidade_acao",
        joinColumns = @JoinColumn(name = "id_funcionalidade", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "id_acao", referencedColumnName = "ID"))
    private Set<Acao> acaos = new HashSet<>();

    public Set<Acao> getacaos() {
        Set<Acao> acaos2 = new HashSet<>();
        acaos2 = acaos;
        return acaos2;
    }

    public void setacaos(Set<Acao> acaos) {
        Set<Acao> acaos2 = new HashSet<>();
        acaos2 = acaos;
        this.acaos = acaos2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmFuncionalidade() {
        return nmFuncionalidade;
    }

    public Funcionalidade nmFuncionalidade(String nmFuncionalidade) {
        this.nmFuncionalidade = nmFuncionalidade;
        return this;
    }

    public void setNmFuncionalidade(String nmFuncionalidade) {
        this.nmFuncionalidade = nmFuncionalidade;
    }

    public String getCdFuncionalidade() {
        return cdFuncionalidade;
    }

    public Funcionalidade cdFuncionalidade(String cdFuncionalidade) {
        this.cdFuncionalidade = cdFuncionalidade;
        return this;
    }

    public void setCdFuncionalidade(String cdFuncionalidade) {
        this.cdFuncionalidade = cdFuncionalidade;
    }

    public String getStExcluido() {
        return stExcluido;
    }

    public Funcionalidade stExcluido(String stExcluido) {
        this.stExcluido = stExcluido;
        return this;
    }

    public void setStExcluido(String stExcluido) {
        this.stExcluido = stExcluido;
    }

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
        return "Funcionalidade{" + "id=" + getId() + ", nmFuncionalidade='" + getNmFuncionalidade() + "'"
            + ", cdFuncionalidade='" + getCdFuncionalidade() + "'" + ", stExcluido='" + getStExcluido() + "'"
            + "}";
    }
}
