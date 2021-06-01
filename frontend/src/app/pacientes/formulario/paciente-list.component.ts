import { Component } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { DatatableParams } from "@nuvem/primeng-components";

@Component({
    selector: 'paciente-list',
    templateUrl: './paciente-list.component.html',
})
export class PacienteListComponent {

    paciente: FormGroup = this.fb.group({
        nome: [],
        prontuario: []
    });

    datatableParams: DatatableParams = {
        rows: 10
    };

    constructor(private fb: FormBuilder) {
    }

}
