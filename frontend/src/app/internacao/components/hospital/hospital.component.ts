import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { EntityAutoComplete } from '@shared/entity-autocomplete.component';
import { Hospital } from '@internacao/models/hospital';
import { HospitalService } from '@internacao/services/hospital.service';

@Component({
    selector: 'app-hospital',
    templateUrl: './hospital.component.html',
})
export class HospitalComponent implements OnInit, EntityAutoComplete {
    @Input() public parentFormGroup: FormGroup;
    @Input() public required = false;
    @Input() public label = 'Hospital';
    @Input() public name = 'hospital';
    public hospitais = new Array<Hospital>();

    constructor(private hospitalService: HospitalService) {}

    ngOnInit(): void {
        this.hospitalService
            .getHospitais(true, 'nome')
            .subscribe((hospitais: Array<Hospital>) => (this.hospitais = hospitais));
    }

    aoDigitar(evento: { originalEvent: any; query: string }): void {
        this.hospitalService
            .getHospitaisPorNome(evento.query, true, 'nome')
            .subscribe((hospitais: Array<Hospital>) => (this.hospitais = hospitais));
    }

    aoDesfocar(): void {
        if (!this.parentFormGroup.get(this.name).value) {
            this.parentFormGroup.get(this.name).setValue(null);
        }
    }
}
