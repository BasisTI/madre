package br.com.basis.madre.madreexames.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import br.com.basis.madre.madreexames.domain.enumeration.Status;

/**
 * A ProcessoAssincronoGrade.
 */
@Entity
@Table(name = "processo_assincrono_grade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-processoassincronograde")
public class ProcessoAssincronoGrade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_termino")
    private Instant dataTermino;

    @Column(name = "ultima_atualizacao")
    private Instant ultimaAtualizacao;

    @Column(name = "qtd_horarios_para_criar")
    private Integer qtdHorariosParaCriar;

    @Column(name = "total_de_horarios_criados")
    private Integer totalDeHorariosCriados;

    @Column(name = "total_completo")
    private Double totalCompleto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public ProcessoAssincronoGrade dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataTermino() {
        return dataTermino;
    }

    public ProcessoAssincronoGrade dataTermino(Instant dataTermino) {
        this.dataTermino = dataTermino;
        return this;
    }

    public void setDataTermino(Instant dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Instant getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public ProcessoAssincronoGrade ultimaAtualizacao(Instant ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
        return this;
    }

    public void setUltimaAtualizacao(Instant ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Integer getQtdHorariosParaCriar() {
        return qtdHorariosParaCriar;
    }

    public ProcessoAssincronoGrade qtdHorariosParaCriar(Integer qtdHorariosParaCriar) {
        this.qtdHorariosParaCriar = qtdHorariosParaCriar;
        return this;
    }

    public void setQtdHorariosParaCriar(Integer qtdHorariosParaCriar) {
        this.qtdHorariosParaCriar = qtdHorariosParaCriar;
    }

    public Integer getTotalDeHorariosCriados() {
        return totalDeHorariosCriados;
    }

    public ProcessoAssincronoGrade totalDeHorariosCriados(Integer totalDeHorariosCriados) {
        this.totalDeHorariosCriados = totalDeHorariosCriados;
        return this;
    }

    public void setTotalDeHorariosCriados(Integer totalDeHorariosCriados) {
        this.totalDeHorariosCriados = totalDeHorariosCriados;
    }

    public Double getTotalCompleto() {
        return totalCompleto;
    }

    public ProcessoAssincronoGrade totalCompleto(Double totalCompleto) {
        this.totalCompleto = totalCompleto;
        return this;
    }

    public void setTotalCompleto(Double totalCompleto) {
        this.totalCompleto = totalCompleto;
    }

    public Status getStatus() {
        return status;
    }

    public ProcessoAssincronoGrade status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessoAssincronoGrade)) {
            return false;
        }
        return id != null && id.equals(((ProcessoAssincronoGrade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoAssincronoGrade{" +
            "id=" + getId() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataTermino='" + getDataTermino() + "'" +
            ", ultimaAtualizacao='" + getUltimaAtualizacao() + "'" +
            ", qtdHorariosParaCriar=" + getQtdHorariosParaCriar() +
            ", totalDeHorariosCriados=" + getTotalDeHorariosCriados() +
            ", totalCompleto=" + getTotalCompleto() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
