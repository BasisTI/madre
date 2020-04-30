import { CrmService } from '@internacao/services/crm.service';
import { CRM } from '@internacao/models/crm';
import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-crm',
    templateUrl: './crm.component.html',
})
export class CrmComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'CRM';
    @Input() public name = 'crm';
    public crms = new Array<CRM>();

    constructor(private crmService: CrmService) {}

    ngOnInit(): void {
        this.crmService.getCRMS(true, 'nome').subscribe((crms: Array<CRM>) => (this.crms = crms));
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.crmService
            .getCRMSPorNome(evento.query, true, 'nome')
            .subscribe((crms: Array<CRM>) => (this.crms = crms));
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
