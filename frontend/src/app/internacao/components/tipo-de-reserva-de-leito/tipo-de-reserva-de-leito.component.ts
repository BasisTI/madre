import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { TipoDeReservaDeLeito } from '@internacao/models/tipo-de-reserva-de-leito';
import { EntityAutoComplete } from './../../../shared/entity-autocomplete.component';
import { TipoDaReservaDeLeitoService } from './../../services/tipo-da-reserva-de-leito.service';

@Component({
    selector: 'app-tipo-de-reserva-de-leito',
    templateUrl: './tipo-de-reserva-de-leito.component.html',
})
export class TipoDeReservaDeLeitoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Tipo da Reserva';
    @Input() public name = 'tipoDaReserva';
    public tiposDeReservaDeLeito = new Array<TipoDeReservaDeLeito>();

    constructor(private tipoDaReservaDeLeitoService: TipoDaReservaDeLeitoService) {}

    ngOnInit() {
        this.tipoDaReservaDeLeitoService
            .getTiposDeReserva()
            .subscribe(
                (tiposDeReservaDeLeito: Array<TipoDeReservaDeLeito>) =>
                    (this.tiposDeReservaDeLeito = tiposDeReservaDeLeito),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.tipoDaReservaDeLeitoService
            .getTiposDeReservaPorNome(evento.query)
            .subscribe(
                (tiposDeReservaDeLeito: Array<TipoDeReservaDeLeito>) =>
                    (this.tiposDeReservaDeLeito = tiposDeReservaDeLeito),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
