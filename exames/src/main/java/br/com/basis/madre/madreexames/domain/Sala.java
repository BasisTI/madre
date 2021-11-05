package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sala.
 */
@Entity
@Table(name = "sala")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-sala")
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSala")
    @SequenceGenerator(name = "seqSala")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "locacao", nullable = false)
    private String locacao;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "unidade_executora_id")
    private Integer unidadeExecutoraId;

    @OneToMany(mappedBy = "sala")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GradeAgendamentoExame> gradeDaSalas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Sala nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocacao() {
        return locacao;
    }

    public Sala locacao(String locacao) {
        this.locacao = locacao;
        return this;
    }

    public void setLocacao(String locacao) {
        this.locacao = locacao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public Sala ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getUnidadeExecutoraId() {
        return unidadeExecutoraId;
    }

    public Sala unidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
        return this;
    }

    public void setUnidadeExecutoraId(Integer unidadeExecutoraId) {
        this.unidadeExecutoraId = unidadeExecutoraId;
    }

    public Set<GradeAgendamentoExame> getGradeDaSalas() {
        return gradeDaSalas;
    }

    public Sala gradeDaSalas(Set<GradeAgendamentoExame> gradeAgendamentoExames) {
        this.gradeDaSalas = gradeAgendamentoExames;
        return this;
    }

    public Sala addGradeDaSala(GradeAgendamentoExame gradeAgendamentoExame) {
        this.gradeDaSalas.add(gradeAgendamentoExame);
        gradeAgendamentoExame.setSala(this);
        return this;
    }

    public Sala removeGradeDaSala(GradeAgendamentoExame gradeAgendamentoExame) {
        this.gradeDaSalas.remove(gradeAgendamentoExame);
        gradeAgendamentoExame.setSala(null);
        return this;
    }

    public void setGradeDaSalas(Set<GradeAgendamentoExame> gradeAgendamentoExames) {
        this.gradeDaSalas = gradeAgendamentoExames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sala)) {
            return false;
        }
        return id != null && id.equals(((Sala) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sala{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", locacao='" + getLocacao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", unidadeExecutoraId=" + getUnidadeExecutoraId() +
            "}";
    }
}
