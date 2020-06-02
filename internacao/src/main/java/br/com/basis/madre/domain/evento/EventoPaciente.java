package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Paciente;
import lombok.Data;

import java.io.Serializable;

@Data
public class EventoPaciente implements Serializable {
    private Paciente paciente;
}
