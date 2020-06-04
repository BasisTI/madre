package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.domain.enumeration.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoTriagem implements Serializable {
    private Triagem triagem;
    private ZonedDateTime dataDeLancamento;
    private TipoEvento tipoDoEvento;
    private String login;
}
