import { ConvenioDeSaudeService } from '@internacao/services/convenio-de-saude.service';
import { ConvenioDeSaude } from './../../models/convenio-de-saude';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';

@Component({
    selector: 'app-convenio-de-saude',
    templateUrl: './convenio-de-saude.component.html',
    styles: [],
})
export class ConvenioDeSaudeComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Convênio de Saúde';
    @Input() public name = 'convenioDeSaude';
    public conveniosDeSaude: Array<ConvenioDeSaude>;

    constructor(private convenioDeSaudeService: ConvenioDeSaudeService) {}

    ngOnInit(): void {
        this.convenioDeSaudeService
            .getConveniosDeSaude(true, 'nome')
            .subscribe(
                (conveniosDeSaude: Array<ConvenioDeSaude>) =>
                    (this.conveniosDeSaude = conveniosDeSaude),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.convenioDeSaudeService
            .getConveniosDeSaudePorNome(evento.query, true, 'nome')
            .subscribe(
                (conveniosDeSaude: Array<ConvenioDeSaude>) =>
                    (this.conveniosDeSaude = conveniosDeSaude),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
