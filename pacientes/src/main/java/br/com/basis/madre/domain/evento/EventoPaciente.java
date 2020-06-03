package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.enumeration.TipoDeMutacao;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Builder
public class EventoPaciente implements Serializable {
    private Paciente paciente;
    private ZonedDateTime dataDeLancamento = ZonedDateTime.now(ZoneId.systemDefault());
    private TipoDeMutacao tipoDoEvento;
}
