package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GrupoAgendamentoExame.
 */
@Entity
@Table(name = "grupo_agendamento_exame")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-grupoagendamentoexame")
public class GrupoAgendamentoExame extends DomainAtivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGrupoAgendamentoExame")
    @SequenceGenerator(name = "seqGrupoAgendamentoExame")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private Integer codigo;

    @Column(name = "agendar_em_conjunto")
    private Boolean agendarEmConjunto;

    @Column(name = "calcular_ocupacao")
    private Boolean calcularOcupacao;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "grupo_agendamento_exame_exame",
               joinColumns = @JoinColumn(name = "grupo_agendamento_exame_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "exame_id", referencedColumnName = "id"))
    private Set<Exame> exames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrupoAgendamentoExame nome(String nome) {
        this.setNome(nome);
        return this;
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

    public GrupoAgendamentoExame ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public Set<Exame> getExames() {
        return new HashSet<>(this.exames);
    }

    public GrupoAgendamentoExame exames(Set<Exame> exames) {
        this.exames = new HashSet<>(exames);
        return this;
    }

    public GrupoAgendamentoExame addExame(Exame exame) {
        this.exames.add(exame);
        exame.getGrupoAgendamentoExames().add(this);
        return this;
    }

    public GrupoAgendamentoExame removeExame(Exame exame) {
        this.exames.remove(exame);
        exame.getGrupoAgendamentoExames().remove(this);
        return this;
    }

    public void setExames(Set<Exame> exames) {
        this.exames = new HashSet<>(exames);
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
