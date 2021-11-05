package br.com.basis.madre.madreexames.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.UUID;

import br.com.basis.madre.madreexames.domain.enumeration.Status;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.ProcessoAssincronoGrade} entity.
 */
public class ProcessoAssincronoGradeDTO implements Serializable {

    private String id;

    private Instant dataInicio;

    private Instant dataTermino;

    private Instant ultimaAtualizacao;

    private Integer qtdHorariosParaCriar;

    private Integer totalDeHorariosCriados;

    private Double totalCompleto;

    private Status status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Instant dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Instant getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Instant ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Integer getQtdHorariosParaCriar() {
        return qtdHorariosParaCriar;
    }

    public void setQtdHorariosParaCriar(Integer qtdHorariosParaCriar) {
        this.qtdHorariosParaCriar = qtdHorariosParaCriar;
    }

    public Integer getTotalDeHorariosCriados() {
        return totalDeHorariosCriados;
    }

    public void setTotalDeHorariosCriados(Integer totalDeHorariosCriados) {
        this.totalDeHorariosCriados = totalDeHorariosCriados;
    }

    public Double getTotalCompleto() {
        return totalCompleto;
    }

    public void setTotalCompleto(Double totalCompleto) {
        this.totalCompleto = totalCompleto;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessoAssincronoGradeDTO)) {
            return false;
        }

        return id != null && id.equals(((ProcessoAssincronoGradeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoAssincronoGradeDTO{" +
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
