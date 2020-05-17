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
    @Input() public situacao: string;
    @Output() public select = new EventEmitter();
    @Output() public blur = new EventEmitter();
    public leitos = new Array<Leito>();

    constructor(private leitoService: LeitoService) {}

    ngOnInit() {
        switch (this.situacao) {
            case 'liberado':
                this.leitoService.obterLeitosLiberados().subscribe((leitos) => {
                    this.leitos = leitos;
                });
                break;
            default:
                break;
        }
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {}

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
