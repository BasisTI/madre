import { EntityAutoComplete } from './../../../shared/entity-autocomplete.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Component, Input, OnInit } from '@angular/core';
import { EspecialidadeService } from '@internacao/services/especialidade.service';
import { Especialidade } from '@internacao/models/especialidade';

@Component({
    selector: 'app-especialidade',
    templateUrl: './especialidade.component.html',
})
export class EspecialidadeComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Especialidade';
    @Input() public name = 'especialidade';
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
    }
}
