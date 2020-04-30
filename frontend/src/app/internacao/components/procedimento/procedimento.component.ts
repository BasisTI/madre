import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Procedimento } from '@internacao/models/procedimento';
import { ProcedimentoService } from '@internacao/services/procedimento.service';
import { EntityAutoComplete } from './../../../shared/entity-autocomplete.component';

@Component({
    selector: 'app-procedimento',
    templateUrl: './procedimento.component.html',
})
export class ProcedimentoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public name = 'procedimento';
    @Input() public label = 'Procedimento';
    public procedimentos = new Array<Procedimento>();

    constructor(private procedimentoService: ProcedimentoService) {}

    ngOnInit(): void {
        this.procedimentoService
            .getProcedimentos(true, 'procedimento')
            .subscribe(
                (procedimentos: Array<Procedimento>) => (this.procedimentos = procedimentos),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.procedimentoService
            .getProcedimentosPorNome(evento.query, true, 'procedimento')
            .subscribe(
                (procedimentos: Array<Procedimento>) => (this.procedimentos = procedimentos),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get('procedimento').value) {
            this.parentFormGroup.get('procedimento').setValue(null);
        }
    }
}
