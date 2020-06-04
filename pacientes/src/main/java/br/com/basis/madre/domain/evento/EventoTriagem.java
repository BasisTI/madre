package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.domain.enumeration.TipoEvento;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
public class EventoTriagem implements Serializable {
    private final Triagem triagem;
    private final ZonedDateTime dataDeLancamento;
    private final TipoEvento tipoDoEvento;
    private final String login;
}
