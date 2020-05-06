import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Especialidade } from '@internacao/models/especialidade';
import { EspecialidadeService } from '@internacao/services/especialidade.service';
import { EntityAutoComplete } from './../../../shared/entity-autocomplete.component';

@Component({
    selector: 'app-especialidade',
    templateUrl: './especialidade.component.html',
})
export class EspecialidadeComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Especialidade';
    @Input() public name = 'especialidade';
    @Output() public select = new EventEmitter();
    public especialidades = new Array<Especialidade>();

    constructor(private especialidadeService: EspecialidadeService) {}

    ngOnInit(): void {
        this.especialidadeService
            .getEspecialidades(true, 'especialidade')
            .subscribe(
                (especialidades: Array<Especialidade>) => (this.especialidades = especialidades),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }) {
        this.especialidadeService
            .getEspecialidadesPorNome(evento.query, true, 'especialidade')
            .subscribe(
                (especialidades: Array<Especialidade>) => (this.especialidades = especialidades),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }

        this.aoSelecionar();
    }

    aoSelecionar(): void {
        this.select.emit(this.parentFormGroup.get(this.name).value);
    }
}
