package br.com.basis.madre.madreexames.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GrupoAgendamentoExame.
 */
@Entity
@Table(name = "grupo_agendamento_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "grupoagendamentoexame")
public class GrupoAgendamentoExame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGrupoAgendamentoExame")
    @SequenceGenerator(name = "seqGrupoAgendamentoExame")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @Column(name = "agendar_em_conjunto")
    private Boolean agendarEmConjunto;

    @Column(name = "calcular_ocupacao")
    private Boolean calcularOcupacao;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "grupo_agendamento_exame_grupo_agendamento",
               joinColumns = @JoinColumn(name = "grupo_agendamento_exame_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "grupo_agendamento_id", referencedColumnName = "id"))
    private Set<Exame> grupoAgendamentos = new HashSet<>();

    @ManyToMany(mappedBy = "exames")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Exame> grupoAgendamentoExames = new HashSet<>();

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

    public GrupoAgendamentoExame nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public GrupoAgendamentoExame codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean isAgendarEmConjunto() {
        return agendarEmConjunto;
    }

    public GrupoAgendamentoExame agendarEmConjunto(Boolean agendarEmConjunto) {
        this.agendarEmConjunto = agendarEmConjunto;
        return this;
    }

    public void setAgendarEmConjunto(Boolean agendarEmConjunto) {
        this.agendarEmConjunto = agendarEmConjunto;
    }

    public Boolean isCalcularOcupacao() {
        return calcularOcupacao;
    }

    public GrupoAgendamentoExame calcularOcupacao(Boolean calcularOcupacao) {
        this.calcularOcupacao = calcularOcupacao;
        return this;
    }

    public void setCalcularOcupacao(Boolean calcularOcupacao) {
        this.calcularOcupacao = calcularOcupacao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public GrupoAgendamentoExame ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Exame> getGrupoAgendamentos() {
        return grupoAgendamentos;
    }

    public GrupoAgendamentoExame grupoAgendamentos(Set<Exame> exames) {
        this.grupoAgendamentos = exames;
        return this;
    }

    public GrupoAgendamentoExame addGrupoAgendamento(Exame exame) {
        this.grupoAgendamentos.add(exame);
        exame.getExames().add(this);
        return this;
    }

    public GrupoAgendamentoExame removeGrupoAgendamento(Exame exame) {
        this.grupoAgendamentos.remove(exame);
        exame.getExames().remove(this);
        return this;
    }

    public void setGrupoAgendamentos(Set<Exame> exames) {
        this.grupoAgendamentos = exames;
    }

    public Set<Exame> getGrupoAgendamentoExames() {
        return grupoAgendamentoExames;
    }

    public GrupoAgendamentoExame grupoAgendamentoExames(Set<Exame> exames) {
        this.grupoAgendamentoExames = exames;
        return this;
    }

    public GrupoAgendamentoExame addGrupoAgendamentoExame(Exame exame) {
        this.grupoAgendamentoExames.add(exame);
        exame.getExames().add(this);
        return this;
    }

    public GrupoAgendamentoExame removeGrupoAgendamentoExame(Exame exame) {
        this.grupoAgendamentoExames.remove(exame);
        exame.getExames().remove(this);
        return this;
    }

    public void setGrupoAgendamentoExames(Set<Exame> exames) {
        this.grupoAgendamentoExames = exames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAgendamentoExame)) {
            return false;
        }
        return id != null && id.equals(((GrupoAgendamentoExame) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAgendamentoExame{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo=" + getCodigo() +
            ", agendarEmConjunto='" + isAgendarEmConjunto() + "'" +
            ", calcularOcupacao='" + isCalcularOcupacao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
