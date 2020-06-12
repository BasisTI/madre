package br.com.basis.madre.prescricao.domain.evento;

import java.io.Serializable;
import java.time.ZonedDateTime;

import br.com.basis.madre.prescricao.domain.Paciente;
import br.com.basis.madre.prescricao.domain.enumeration.TipoEvento;
import lombok.Data;

@Data
public class EventoPaciente implements Serializable {

    private static final long serialVersionUID = 1L;
    private Paciente paciente;
    private ZonedDateTime dataDeLancamento;
    private TipoEvento tipoDoEvento;
    private String login;

}
