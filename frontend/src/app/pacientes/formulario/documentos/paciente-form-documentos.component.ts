import { Component, Input, OnInit } from '@angular/core';
import { OrgaoEmissorService } from './orgao-emissor.service';
import { UfService } from '../municipio/uf.service';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'paciente-form-documentos',
    templateUrl: './paciente-form-documentos.component.html',
    styleUrls: ['./paciente-form-documentos.component.css'],
})
export class PacienteDocumentosFormComponent implements OnInit {
    @Input()
    public formGroup: FormGroup;

    constructor(public orgaoEmissorService: OrgaoEmissorService, public ufService: UfService) {}

    ngOnInit() {
        if (this.formGroup.get('documento').get('orgaoEmissorId').value != null) {
            this.orgaoEmissorService
                .find(this.formGroup.get('documento').get('orgaoEmissorId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ documento: { orgaoEmissorId: res } });
                });
        }
        if (this.formGroup.get('documento').get('ufId').value != null) {
            this.ufService
                .find(this.formGroup.get('documento').get('ufId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ documento: { ufId: res } });
                });
        }
    }
}
