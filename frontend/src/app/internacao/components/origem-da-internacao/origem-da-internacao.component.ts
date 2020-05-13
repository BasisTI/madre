import { OrigemDaInternacaoService } from '@internacao/services/origem-da-internacao.service';
import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { Component, OnInit, Input } from '@angular/core';
import { OrigemDaInternacao } from '@internacao/models/origem-da-internacao';

@Component({
    selector: 'app-origem-da-internacao',
    templateUrl: './origem-da-internacao.component.html',
})
export class OrigemDaInternacaoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Origem da Internação';
    @Input() public name = 'origemDaInternacao';
    public origensDaInternacao = new Array<OrigemDaInternacao>();

    constructor(private origemDaInternacaoService: OrigemDaInternacaoService) {}

    ngOnInit(): void {
        this.origemDaInternacaoService
            .getOrigensDaInternacao(true, 'nome')
            .subscribe(
                (origensDaInternacao: Array<OrigemDaInternacao>) =>
                    (this.origensDaInternacao = origensDaInternacao),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.origemDaInternacaoService
            .getOrigensDaInternacaoPorNome(evento.query, true, 'nome')
            .subscribe(
                (origensDaInternacao: Array<OrigemDaInternacao>) =>
                    (this.origensDaInternacao = origensDaInternacao),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
