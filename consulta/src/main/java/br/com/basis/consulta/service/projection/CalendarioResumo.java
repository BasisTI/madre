package br.com.basis.consulta.service.projection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CalendarioResumo {

        Long getId();

        Enum getTurno();
        Enum getTipoPagador();

    @JsonProperty("title")
    String getNumeroSala();

    @JsonProperty("start")
    ZonedDateTime getDataHoraDaConsulta();

}
