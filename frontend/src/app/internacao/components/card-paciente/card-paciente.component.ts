import { Component, Input, OnInit } from '@angular/core';
import { PacienteService } from '@internacao/services/paciente.service';
import { Paciente } from '@internacao/models/paciente';

@Component({
    selector: 'app-card-paciente',
    template: `
        <div class="ui-g ui-fluid">
            <p-fieldset legend="Paciente">
                <div class="ui-g">
                    <div class="ui-g-4">
                        <label>Identificador</label>
                        <input pInputText value="{{ this.getIdentificador() }}"/>
                    </div>
                    <div class="ui-g-8">
                        <label>Nome do Paciente</label>
                        <input pInputText value="{{ this.getNomeDoPaciente() }}"/>
                    </div>
                </div>
            </p-fieldset>
        </div>
    `,
})
export class CardPacienteComponent implements OnInit {
    /**
     * TODO: Deve ser substituído por 'prontuário'
     */
    @Input() private identificador: number;
    private paciente: Paciente;

    constructor(private pacienteService: PacienteService) {
    }

    ngOnInit(): void {
        this.pacienteService.obterPacientePorId(this.identificador).subscribe(paciente => {
            this.paciente = paciente;
        });
    }

    public getNomeDoPaciente(): string | null {
        if (!this.paciente?.nome) {
            return null;
        }

        return this.paciente.nome;
    }

    public getIdentificador(): number {
        return this.identificador;
    }
}
