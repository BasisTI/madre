import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { LocalDeAtendimento } from '@internacao/models/local-de-atendimento';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { LocalDeAtendimentoService } from '@internacao/services/local-de-atendimento.service';

@Component({
    selector: 'app-local-de-atendimento',
    templateUrl: './local-de-atendimento.component.html',
})
export class LocalDeAtendimentoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Local de Atendimento';
    @Input() public name = 'localDeAtendimento';
    public locaisDeAtendimento = new Array<LocalDeAtendimento>();

    constructor(private localDeAtendimentoService: LocalDeAtendimentoService) {}

    ngOnInit(): void {
        this.localDeAtendimentoService
            .getLocaisDeAtendimento(true, 'nome')
            .subscribe(
                (locaisDeAtendimento: Array<LocalDeAtendimento>) =>
                    (this.locaisDeAtendimento = locaisDeAtendimento),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.localDeAtendimentoService
            .getLocaisDeAtendimentoPorNome(evento.query, true, 'nome')
            .subscribe(
                (locaisDeAtendimento: Array<LocalDeAtendimento>) =>
                    (this.locaisDeAtendimento = locaisDeAtendimento),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
