import { Component, Input } from "@angular/core";
import { OrgaoEmissorService } from "./orgao-emissor.service";
import { UfService } from "./uf.service";
import { FormGroup } from "@angular/forms";

@Component({
    selector: 'paciente-form-documentos',
    templateUrl: './paciente-form-documentos.component.html',
})
export class PacienteDocumentosFormComponent {

    @Input() 
    public formGroup: FormGroup;

    constructor(
        public orgaoEmissorService: OrgaoEmissorService,
        public ufService: UfService) {
    }

}
