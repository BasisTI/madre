import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { Component } from '@angular/core';

@Component({
    selector: 'app-dar-alta-ao-paciente',
    templateUrl: './dar-alta-ao-paciente.component.html',
    styleUrls: ['./dar-alta-ao-paciente.component.css'],
})
export class DarAltaAoPacienteComponent {
    private altaCadastrada: DarAltaAoPaciente = {};

    constructor() {}

    public get alta(): DarAltaAoPaciente {
        return this.altaCadastrada;
    }

    onAltaSalva(salvaAlta: DarAltaAoPaciente) {
        this.altaCadastrada = salvaAlta;
        console.log(salvaAlta);
    }
}
