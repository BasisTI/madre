package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.enumeration.TipoEvento;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
public class EventoPaciente implements Serializable {
    private final Paciente paciente;
    private final ZonedDateTime dataDeLancamento;
    private final TipoEvento tipoDoEvento;
}
