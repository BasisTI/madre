import { MotivoDoBloqueioDeLeitoService } from './../../services/motivo-do-bloqueio-de-leito.service';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MotivoDoBloqueioDeLeito } from '@internacao/models/motivo-do-bloqueio-de-leito';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';

@Component({
    selector: 'app-motivo-do-bloqueio-de-leito',
    templateUrl: './motivo-do-bloqueio-de-leito.component.html',
})
export class MotivoDoBloqueioDeLeitoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Motivo';
    @Input() public name = 'motivo';
    public motivosDoBloqueioDeLeito = new Array<MotivoDoBloqueioDeLeito>();

    constructor(private motivoDoBloqueioDeLeitoService: MotivoDoBloqueioDeLeitoService) {}

    ngOnInit() {
        this.motivoDoBloqueioDeLeitoService
            .getMotivosDoBloqueioDeLeito()
            .subscribe(
                (motivosDoBloqueioDeLeito: Array<MotivoDoBloqueioDeLeito>) =>
                    (this.motivosDoBloqueioDeLeito = motivosDoBloqueioDeLeito),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.motivoDoBloqueioDeLeitoService
            .getMotivosDoBloqueioDeLeitoPorNome(evento.query)
            .subscribe(
                (motivosDoBloqueioDeLeito: Array<MotivoDoBloqueioDeLeito>) =>
                    (this.motivosDoBloqueioDeLeito = motivosDoBloqueioDeLeito),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
