package br.com.basis.madre.cadastros.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PerfilFuncionalidadeAcao.
 */
@Entity
@Table(name = "perfil_funcionalidade_acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "perfil_funcionalidade_acao")
public class PerfilFuncionalidadeAcao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

    @NotNull
    @Column(name = "id_perfil", nullable = false)
    private Long idPerfil;

    @NotNull
    @Column(name = "id_funcionalidade_acao", nullable = false)
    private Integer idFuncionalidadeAcao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private FuncionalidadeAcao funcionalidadeAcao;

    public PerfilFuncionalidadeAcao(Long idPerfil, Integer idFuncionalidadeAcao) {
        super();
        this.idPerfil = idPerfil;
        this.idFuncionalidadeAcao = idFuncionalidadeAcao;
    }

    public PerfilFuncionalidadeAcao() {
    }

    /**
     * @return the funcionalidadeAcao
     */
    public FuncionalidadeAcao getFuncionalidadeAcao() {
        return funcionalidadeAcao;
    }

    /**
     * @param funcionalidadeAcao the funcionalidadeAcao to set
     */
    public void setFuncionalidadeAcao(FuncionalidadeAcao funcionalidadeAcao) {
        this.funcionalidadeAcao = funcionalidadeAcao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public PerfilFuncionalidadeAcao idPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
        return this;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdFuncionalidadeAcao() {
        return idFuncionalidadeAcao;
    }

    public PerfilFuncionalidadeAcao idFuncionalidadeAcao(Integer idFuncionalidadeAcao) {
        this.idFuncionalidadeAcao = idFuncionalidadeAcao;
        return this;
    }

    public void setIdFuncionalidadeAcao(Integer idFuncionalidadeAcao) {
        this.idFuncionalidadeAcao = idFuncionalidadeAcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PerfilFuncionalidadeAcao perfilFuncionalidadeAcao = (PerfilFuncionalidadeAcao) o;
        if (perfilFuncionalidadeAcao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfilFuncionalidadeAcao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilFuncionalidadeAcao{" +
            "id=" + getId() +
            ", idPerfil=" + getIdPerfil() +
            ", idFuncionalidadeAcao=" + getIdFuncionalidadeAcao() +
            "}";
    }
}
