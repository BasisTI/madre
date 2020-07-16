package br.com.basis.consulta.service.dto;

import br.com.basis.consulta.domain.enumeration.TipoPagador;
import br.com.basis.consulta.domain.enumeration.Turno;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
public class EmergenciaDTO implements Serializable {

    private Long id;

    private Long numeroConsulta;

    @NotNull
    private ZonedDateTime dataHoraDaConsulta;

    @Max(value = 20L)
    private Long grade;

    @Size(max = 20)
    private String numeroSala;

    private Turno turno;

    private TipoPagador tipoPagador;

    private Boolean gradesDisponiveis;

    private Long clinicaCentralId;

    @Size(max = 240)
    private String justificativa;

    @Size(max = 240)
    private String observacoes;

    private Long pacienteId;


    private Long condicaoDeAtendimentoId;

    private Long formaDeAgendamentoId;

    public Boolean isGradesDisponiveis() {
        return gradesDisponiveis;
    }
}
