package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Acao.
 */
@Entity
@Table(name = "acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "acao")
public class Acao implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nm_acao", length = 20, nullable = false)
    private String nmAcao;

    @NotNull
    @Size(max = 8)
    @Column(name = "cd_acao", length = 8, nullable = false)
    private String cdAcao;

    @JsonIgnore
    @ManyToMany(mappedBy = "acaos")
    private Set<Funcionalidade> funcionalidades = new HashSet<>();

    public Set<Funcionalidade> getFuncionalidades() {
        Set<Funcionalidade> funcionalidades1;
        funcionalidades1 = funcionalidades;
        return funcionalidades1;
    }

    public void setFuncionalidades(Set<Funcionalidade> funcionalidades) {
        Set<Funcionalidade> funcionalidades1;
        funcionalidades1 = funcionalidades;
        this.funcionalidades = funcionalidades1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmAcao() {
        return nmAcao;
    }

    public Acao nmAcao(String nmAcao) {
        this.nmAcao = nmAcao;
        return this;
    }

    public void setNmAcao(String nmAcao) {
        this.nmAcao = nmAcao;
    }

    public String getCdAcao() {
        return cdAcao;
    }

    public Acao cdAcao(String cdAcao) {
        this.cdAcao = cdAcao;
        return this;
    }

    public void setCdAcao(String cdAcao) {
        this.cdAcao = cdAcao;
    }

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
        return "Acao{" + "id=" + getId() + ", nmAcao='" + getNmAcao() + "'" + ", cdAcao='" + getCdAcao() + "'"
            + "}";
    }
}
