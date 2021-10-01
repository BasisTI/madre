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
 * A TipoDeMarcacao.
 */
@Entity
@Table(name = "tipo_de_marcacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tipodemarcacao")
public class TipoDeMarcacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTipoDeMarcacao")
    @SequenceGenerator(name = "seqTipoDeMarcacao")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "tipoDeMarcacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<HorarioExame> horarioMarcados = new HashSet<>();

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

    public TipoDeMarcacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public TipoDeMarcacao ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<HorarioExame> getHorarioMarcados() {
        return horarioMarcados;
    }

    public TipoDeMarcacao horarioMarcados(Set<HorarioExame> horarioExames) {
        this.horarioMarcados = horarioExames;
        return this;
    }

    public TipoDeMarcacao addHorarioMarcado(HorarioExame horarioExame) {
        this.horarioMarcados.add(horarioExame);
        horarioExame.setTipoDeMarcacao(this);
        return this;
    }

    public TipoDeMarcacao removeHorarioMarcado(HorarioExame horarioExame) {
        this.horarioMarcados.remove(horarioExame);
        horarioExame.setTipoDeMarcacao(null);
        return this;
    }

    public void setHorarioMarcados(Set<HorarioExame> horarioExames) {
        this.horarioMarcados = horarioExames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeMarcacao)) {
            return false;
        }
        return id != null && id.equals(((TipoDeMarcacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDeMarcacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
