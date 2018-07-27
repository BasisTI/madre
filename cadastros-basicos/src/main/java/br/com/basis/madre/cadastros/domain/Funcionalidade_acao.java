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
 * A Funcionalidade_acao.
 */
@Entity
@Table(name = "funcionalidade_acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "funcionalidade_acao")
public class Funcionalidade_acao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_funcionalidade", nullable = false)
    private Long id_funcionalidade;

    @NotNull
    @Column(name = "id_acao", nullable = false)
    private Long id_acao;

//    @ManyToMany
//	private List<Funcionalidade> funcionalidades = new ArrayList<>();
//    @ManyToMany
//    private List<Acao> acoes = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_funcionalidade() {
        return id_funcionalidade;
    }

    public Funcionalidade_acao id_funcionalidade(Long id_funcionalidade) {
        this.id_funcionalidade = id_funcionalidade;
        return this;
    }

    public void setId_funcionalidade(Long id_funcionalidade) {
        this.id_funcionalidade = id_funcionalidade;
    }

    public Long getId_acao() {
        return id_acao;
    }

    public Funcionalidade_acao id_acao(Long id_acao) {
        this.id_acao = id_acao;
        return this;
    }

    public void setId_acao(Long id_acao) {
        this.id_acao = id_acao;
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
        Funcionalidade_acao funcionalidade_acao = (Funcionalidade_acao) o;
        if (funcionalidade_acao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionalidade_acao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Funcionalidade_acao{" +
            "id=" + getId() +
            ", id_funcionalidade=" + getId_funcionalidade() +
            ", id_acao=" + getId_acao() +
            "}";
    }
}
