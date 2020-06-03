package br.com.basis.madre.domain.evento;

import br.com.basis.madre.domain.Paciente;

public class EventoPaciente {
    private Paciente paciente;

    public EventoPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
