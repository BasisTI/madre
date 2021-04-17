package br.com.basis.consulta.service.projection;

import br.com.basis.consulta.domain.CondicaoDeAtendimento;
import br.com.basis.consulta.domain.enumeration.TipoPagador;
import br.com.basis.consulta.domain.enumeration.Turno;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CalendarioResumo {

        Long getId();

        Turno getTurno();
        TipoPagador getTipoPagador();

    String getNumeroSala();

    @JsonProperty("title")
    String getEspecialidade();

    String getPacienteId();

    @JsonProperty("start")
    ZonedDateTime getDataHoraDaConsulta();

}
