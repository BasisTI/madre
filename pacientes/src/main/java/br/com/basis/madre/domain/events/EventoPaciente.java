package br.com.basis.madre.domain.events;

import br.com.basis.madre.domain.Paciente;

public class EventoPaciente {
    private Paciente paciente;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
