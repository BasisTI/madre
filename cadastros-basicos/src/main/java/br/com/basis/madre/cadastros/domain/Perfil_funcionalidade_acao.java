package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Perfil_funcionalidade_acao.
 */
@Entity
@Table(name = "perfil_funcionalidade_acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "perfil_funcionalidade_acao")
public class Perfil_funcionalidade_acao implements Serializable {

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

    public Perfil_funcionalidade_acao(Long id_perfil, Integer id_funcionalidade_acao) {
		super();
		this.id_perfil = id_perfil;
		this.id_funcionalidade_acao = id_funcionalidade_acao;
	}
    
    public Perfil_funcionalidade_acao() {
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_perfil() {
        return id_perfil;
    }

    public Perfil_funcionalidade_acao id_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
        return this;
    }

    public void setId_perfil(Long id_perfil) {
        this.id_perfil = id_perfil;
    }

    public Integer getId_funcionalidade_acao() {
        return id_funcionalidade_acao;
    }

    public Perfil_funcionalidade_acao id_funcionalidade_acao(Integer id_funcionalidade_acao) {
        this.id_funcionalidade_acao = id_funcionalidade_acao;
        return this;
    }

    public void setId_funcionalidade_acao(Integer id_funcionalidade_acao) {
        this.id_funcionalidade_acao = id_funcionalidade_acao;
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
        Perfil_funcionalidade_acao perfil_funcionalidade_acao = (Perfil_funcionalidade_acao) o;
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
        return "Perfil_funcionalidade_acao{" +
            "id=" + getId() +
            ", id_perfil=" + getId_perfil() +
            ", id_funcionalidade_acao=" + getId_funcionalidade_acao() +
            "}";
    }
}
