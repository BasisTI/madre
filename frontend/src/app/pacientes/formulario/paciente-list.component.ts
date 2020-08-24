import { Component } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";

@Component({
    selector: 'paciente-list',
    templateUrl: './paciente-list.component.html',
})
export class PacienteListComponent {

    paciente: FormGroup = this.fb.group({
        nome: []
    });

    constructor(private fb: FormBuilder) {
    }

}
