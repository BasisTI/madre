package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dia.
 */
@Entity
@Table(name = "dia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-dia")
public class Dia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqDia")
    @SequenceGenerator(name = "seqDia")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "dias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GradeAgendamentoExame> gradeAgendamentoExames = new HashSet<>();

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

    public Dia nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<GradeAgendamentoExame> getGradeAgendamentoExames() {
        return gradeAgendamentoExames;
    }

    public Dia gradeAgendamentoExames(Set<GradeAgendamentoExame> gradeAgendamentoExames) {
        this.gradeAgendamentoExames = gradeAgendamentoExames;
        return this;
    }

    public Dia addGradeAgendamentoExame(GradeAgendamentoExame gradeAgendamentoExame) {
        this.gradeAgendamentoExames.add(gradeAgendamentoExame);
        gradeAgendamentoExame.getDias().add(this);
        return this;
    }

    public Dia removeGradeAgendamentoExame(GradeAgendamentoExame gradeAgendamentoExame) {
        this.gradeAgendamentoExames.remove(gradeAgendamentoExame);
        gradeAgendamentoExame.getDias().remove(this);
        return this;
    }

    public void setGradeAgendamentoExames(Set<GradeAgendamentoExame> gradeAgendamentoExames) {
        this.gradeAgendamentoExames = gradeAgendamentoExames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dia)) {
            return false;
        }
        return id != null && id.equals(((Dia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dia{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
