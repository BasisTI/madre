package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.domain.enumeration.TipoDeMutacao;
import lombok.Data;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class EventoPaciente implements Serializable {
    private Paciente paciente;
    private ZonedDateTime dataDeLancamento = ZonedDateTime.now(ZoneId.systemDefault());
    private TipoDeMutacao tipoDoEvento;

    public EventoPaciente paciente(Paciente paciente) {
        this.paciente = paciente;

        return this;
    }

    public EventoPaciente tipoDoEvento(TipoDeMutacao tipoDeMutacao) {
        tipoDoEvento = tipoDeMutacao;

        return this;
    }
}
