import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { Component, OnInit, Input } from '@angular/core';
import { CaraterDaInternacaoService } from '@internacao/services/carater-da-internacao.service';
import { CaraterDaInternacao } from '@internacao/models/carater-da-internacao';

@Component({
    selector: 'app-carater-da-internacao',
    templateUrl: './carater-da-internacao.component.html',
    styles: [],
})
export class CaraterDaInternacaoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Caráter da Internação';
    @Input() public name = 'caraterDaInternacao';
    public caracteresDeIntenacao: Array<CaraterDaInternacao>;

    constructor(private caraterDaInternacaoService: CaraterDaInternacaoService) {}

    ngOnInit(): void {
        this.caraterDaInternacaoService
            .getCaracteresDaInternacao(true, 'nome')
            .subscribe(
                (caracteresDeIntenacao: Array<CaraterDaInternacao>) =>
                    (this.caracteresDeIntenacao = caracteresDeIntenacao),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.caraterDaInternacaoService
            .getCaracteresDaInternacaoPorNome(evento.query, true, 'nome')
            .subscribe(
                (caracteresDeIntenacao: Array<CaraterDaInternacao>) =>
                    (this.caracteresDeIntenacao = caracteresDeIntenacao),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
