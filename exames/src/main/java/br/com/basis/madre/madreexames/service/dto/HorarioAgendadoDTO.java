package br.com.basis.madre.madreexames.service.dto;

import br.com.basis.madre.madreexames.domain.enumeration.Dia;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * A DTO for the {@link br.com.basis.madre.madreexames.domain.HorarioAgendado} entity.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "madre-exames-horarioagendado")
@NoArgsConstructor
@AllArgsConstructor
public class HorarioAgendadoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant horaInicio;

    private Instant horaFim;

    @Min(1)
    private Integer numeroDeHorarios;

    @NotNull
    private Dia dia;

    @NotNull
    private Duration duracao;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Boolean exclusivo;


    private Long tipoHorarioId;

    private String tipoHorarioTipoDeMarcacao;

    private Long gradeDeAgendamentoId;

    public HorarioAgendadoDTO(Long id, Instant horaInicio, Instant horaFim, Integer numeroDeHorarios, Dia dia) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.numeroDeHorarios = numeroDeHorarios;
        this.dia = dia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getNumeroDeHorarios() {
        return numeroDeHorarios;
    }

    public void setNumeroDeHorarios(Integer numeroDeHorarios) {
        this.numeroDeHorarios = numeroDeHorarios;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean isExclusivo() {
        return exclusivo;
    }

    public void setExclusivo(Boolean exclusivo) {
        this.exclusivo = exclusivo;
    }

    public Long getTipoHorarioId() {
        return tipoHorarioId;
    }

    public void setTipoHorarioId(Long tipoDeMarcacaoId) {
        this.tipoHorarioId = tipoDeMarcacaoId;
    }

    public String getTipoHorarioTipoDeMarcacao() {
        return tipoHorarioTipoDeMarcacao;
    }

    public void setTipoHorarioTipoDeMarcacao(String tipoDeMarcacaoTipoDeMarcacao) {
        this.tipoHorarioTipoDeMarcacao = tipoDeMarcacaoTipoDeMarcacao;
    }

    public Long getGradeDeAgendamentoId() {
        return gradeDeAgendamentoId;
    }

    public void setGradeDeAgendamentoId(Long gradeDeAgendamentoId) {
        this.gradeDeAgendamentoId = gradeDeAgendamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioAgendadoDTO)) {
            return false;
        }

        return id != null && id.equals(((HorarioAgendadoDTO) o).id);
    }

    @AssertTrue(message = "Hora fim deve ser depois de hora início")
    private boolean isHoraInicioAntesDeHoraFim() {
        return getHoraFim() == null || getHoraInicio().isBefore(getHoraFim());
    }

    @AssertTrue(message = "Não informar hora fim e número de horários ao mesmo tempo")
    private boolean isHoraFimOuNumeroDeHorariosNulo() {
        return getHoraFim() == null ^ getNumeroDeHorarios() == null;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioAgendadoDTO{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", numeroDeHorarios=" + getNumeroDeHorarios() +
            ", dia='" + getDia() + "'" +
            ", duracao='" + getDuracao() + "'" +
            ", ativo='" + isAtivo() + "'" +
            ", exclusivo='" + isExclusivo() + "'" +
            ", tipoHorarioId=" + getTipoHorarioId() +
            ", tipoHorarioTipoDeMarcacao='" + getTipoHorarioTipoDeMarcacao() + "'" +
            ", gradeDeAgendamentoId=" + getGradeDeAgendamentoId() +
            "}";
    }
}
