import { ProcedenciaService } from '@internacao/services/procedencia.service';
import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { Component, OnInit, Input } from '@angular/core';
import { Procedencia } from '@internacao/models/procedencia';

@Component({
    selector: 'app-procedencia',
    templateUrl: './procedencia.component.html',
})
export class ProcedenciaComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required: false;
    @Input() public label = 'Procêdencia';
    @Input() public name = 'procedencia';
    public procedencias: Array<Procedencia>;

    constructor(private procedenciaService: ProcedenciaService) {}

    ngOnInit(): void {
        this.procedenciaService
            .getProcedencias(true, 'descricao')
            .subscribe((procedencias: Array<Procedencia>) => (this.procedencias = procedencias));
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.procedenciaService
            .getProcedenciasPorNome(evento.query, true, 'descricao')
            .subscribe((procedencias: Array<Procedencia>) => (this.procedencias = procedencias));
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
