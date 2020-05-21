package br.com.basis.madre.service.projection;

import br.com.basis.madre.domain.Leito;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface EventoCalendario {
    Long getId();

    Leito getLeito();

    ZonedDateTime getDataDoLancamento();

    @JsonProperty("title")
    String getTipoDoEventoNome();

    @JsonProperty("start")
    ZonedDateTime getDataInicio();

    @JsonProperty("end")
    ZonedDateTime getDataFim();

    interface TipoDoEvento {
        String getNome();
    }
}
