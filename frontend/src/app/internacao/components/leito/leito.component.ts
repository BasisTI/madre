import { LeitoService } from '@internacao/services/leito.service';
import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Leito } from '@internacao/models/leito';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';

@Component({
    selector: 'app-leito',
    templateUrl: './leito.component.html',
})
export class LeitoComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Leito';
    @Input() public name = 'leito';
    @Output() public select = new EventEmitter();
    @Output() public blur = new EventEmitter();
    public leitos = new Array<Leito>();

    constructor(private leitoService: LeitoService) {}

    ngOnInit() {
        this.leitoService
            .getLeitosDesocupados()
            .subscribe((leitos: Array<Leito>) => (this.leitos = leitos));
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.leitoService
            .getLeitosDesocupadosPorNome(evento.query)
            .subscribe((leitos: Array<Leito>) => (this.leitos = leitos));
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
            this.blur.emit(null);
        }
    }

    aoSelecionar(): void {
        if (this.parentFormGroup.get(this.name).value) {
            this.select.emit(this.parentFormGroup.get(this.name).value);
        }
    }
}
