import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { PlanoDeSaudeService } from '@internacao/services/plano-de-saude.service';
import { EntityAutoComplete } from './../../../shared/entity-autocomplete.component';
import { PlanoDeSaude } from './../../models/plano-de-saude';

@Component({
    selector: 'app-plano-de-saude',
    templateUrl: './plano-de-saude.component.html',
    styles: [],
})
export class PlanoDeSaudeComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Plano de Saúde';
    @Input() public name = 'planoDeSaude';
    public planosDeSaude = new Array<PlanoDeSaude>();

    constructor(private planoDeSaudeService: PlanoDeSaudeService) {}

    ngOnInit(): void {
        this.planoDeSaudeService
            .getPlanosDeSaude(true, 'nome')
            .subscribe(
                (planosDeSaude: Array<PlanoDeSaude>) => (this.planosDeSaude = planosDeSaude),
            );
    }

    aoDigitar(evento: { originalEvent: any; query: string }) {
        this.planoDeSaudeService
            .getPlanosDeSaudePorNome(evento.query, true, 'nome')
            .subscribe(
                (planosDeSaude: Array<PlanoDeSaude>) => (this.planosDeSaude = planosDeSaude),
            );
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
