package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A FuncionalidadeAcao.
 */
@Entity
@Table(name = "funcionalidade_acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "funcionalidade_acao")
public class FuncionalidadeAcao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_funcionalidade", nullable = false)
    private Integer idFuncionalidade;

    @NotNull
    @Column(name = "id_acao", nullable = false)
    private Integer idAcao;

    private PerfilFuncionalidadeAcao perfilFuncionalidadeAcao;

    /**
     * @return the perfilFuncionalidadeAcao
     */
    public PerfilFuncionalidadeAcao getPerfilFuncionalidadeAcao() {
        return perfilFuncionalidadeAcao;
    }

    /**
     * @param perfilFuncionalidadeAcao the perfilFuncionalidadeAcao to set
     */
    public void setPerfilFuncionalidadeAcao(PerfilFuncionalidadeAcao perfilFuncionalidadeAcao) {
        this.perfilFuncionalidadeAcao = perfilFuncionalidadeAcao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFuncionalidade() {
        return idFuncionalidade;
    }

    public FuncionalidadeAcao idFuncionalidade(Integer idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
        return this;
    }

    public void setIdFuncionalidade(Integer idFuncionalidade) {
        this.idFuncionalidade = idFuncionalidade;
    }

    public Integer getIdAcao() {
        return idAcao;
    }

    public FuncionalidadeAcao idAcao(Integer idAcao) {
        this.idAcao = idAcao;
        return this;
    }

    public void setIdAcao(Integer idAcao) {
        this.idAcao = idAcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FuncionalidadeAcao funcionalidadeAcao = (FuncionalidadeAcao) o;
        if (funcionalidadeAcao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionalidadeAcao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FuncionalidadeAcao{" +
            "id=" + getId() +
            ", idFuncionalidade=" + getIdFuncionalidade() +
            ", idAcao=" + getIdAcao() +
            "}";
    }
}
