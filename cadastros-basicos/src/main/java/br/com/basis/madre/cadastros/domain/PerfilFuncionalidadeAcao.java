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
	private Long id_perfil;

	@NotNull
	@Column(name = "id_funcionalidade_acao", nullable = false)
	private Integer id_funcionalidade_acao;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private FuncionalidadeAcao funcionalidade_acao;

    public PerfilFuncionalidadeAcao(Long id_perfil, Integer id_funcionalidade_acao) {
		super();
		this.id_perfil = id_perfil;
		this.id_funcionalidade_acao = id_funcionalidade_acao;
	}

    public PerfilFuncionalidadeAcao() {
	}

    /**
     * @return the funcionalidade_acao
     */
    public FuncionalidadeAcao getFuncionalidade_acao() {
        return funcionalidade_acao;
    }

    /**
     * @param funcionalidade_acao the funcionalidade_acao to set
     */
    public void setFuncionalidade_acao(FuncionalidadeAcao funcionalidade_acao) {
        this.funcionalidade_acao = funcionalidade_acao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_perfil() {
        return id_perfil;
    }

    public PerfilFuncionalidadeAcao id_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
        return this;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public Integer getId_funcionalidade_acao() {
        return id_funcionalidade_acao;
    }

    public PerfilFuncionalidadeAcao id_funcionalidade_acao(Integer id_funcionalidade_acao) {
        this.id_funcionalidade_acao = id_funcionalidade_acao;
        return this;
    }

    public void setId_funcionalidade_acao(Integer id_funcionalidade_acao) {
        this.id_funcionalidade_acao = id_funcionalidade_acao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PerfilFuncionalidadeAcao perfil_funcionalidade_acao = (PerfilFuncionalidadeAcao) o;
        if (perfil_funcionalidade_acao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), perfil_funcionalidade_acao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PerfilFuncionalidadeAcao{" +
            "id=" + getId() +
            ", id_perfil=" + getId_perfil() +
            ", id_funcionalidade_acao=" + getId_funcionalidade_acao() +
            "}";
    }
}
