import { OrigemDaReservaDeLeitoService } from '@internacao/services/origem-da-reserva-de-leito.service';
import { OrigemDaReservaDeLeito } from '@internacao/models/origem-da-reserva-de-leito';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';

@Component({
    selector: 'app-origem-da-reserva-de-leito',
    templateUrl: './origem-da-reserva-de-leito.component.html',
})
export class OrigemDaReservaDeLeitoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Origem';
    @Input() public name = 'origem';
    public origensDaReservaDeLeito = new Array<OrigemDaReservaDeLeito>();

    constructor(private origemDaReservaDeLeitoService: OrigemDaReservaDeLeitoService) {}

    ngOnInit() {
        this.origemDaReservaDeLeitoService
            .getOrigensDaReservaDeLeito()
            .subscribe(
                (origensDaReservaDeLeito: Array<OrigemDaReservaDeLeito>) =>
                    (this.origensDaReservaDeLeito = origensDaReservaDeLeito),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.origemDaReservaDeLeitoService
            .getOrigensDaReservaDeLeitoPorNome(evento.query)
            .subscribe(
                (origensDaReservaDeLeito: Array<OrigemDaReservaDeLeito>) =>
                    (this.origensDaReservaDeLeito = origensDaReservaDeLeito),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
