package br.com.basis.madre.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "equipe")
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_do_conselho", nullable = false)
    private Long numeroDoConselho;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties("equipes")
    private SolicitacaoDeInternacao solicitacaoDeInternacao;

    @OneToMany(mappedBy = "equipe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Especialidade> especialidades = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroDoConselho() {
        return numeroDoConselho;
    }

    public Equipe numeroDoConselho(Long numeroDoConselho) {
        this.numeroDoConselho = numeroDoConselho;
        return this;
    }

    public void setNumeroDoConselho(Long numeroDoConselho) {
        this.numeroDoConselho = numeroDoConselho;
    }

    public String getNome() {
        return nome;
    }

    public Equipe nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public SolicitacaoDeInternacao getSolicitacaoDeInternacao() {
        return solicitacaoDeInternacao;
    }

    public Equipe solicitacaoDeInternacao(SolicitacaoDeInternacao solicitacaoDeInternacao) {
        this.solicitacaoDeInternacao = solicitacaoDeInternacao;
        return this;
    }

    public void setSolicitacaoDeInternacao(SolicitacaoDeInternacao solicitacaoDeInternacao) {
        this.solicitacaoDeInternacao = solicitacaoDeInternacao;
    }

    public Set<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public Equipe especialidades(Set<Especialidade> especialidades) {
        this.especialidades = especialidades;
        return this;
    }

    public Equipe addEspecialidade(Especialidade especialidade) {
        this.especialidades.add(especialidade);
        especialidade.setEquipe(this);
        return this;
    }

    public Equipe removeEspecialidade(Especialidade especialidade) {
        this.especialidades.remove(especialidade);
        especialidade.setEquipe(null);
        return this;
    }

    public void setEspecialidades(Set<Especialidade> especialidades) {
        this.especialidades = especialidades;
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
        Equipe equipe = (Equipe) o;
        if (equipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", numeroDoConselho=" + getNumeroDoConselho() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
