import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-prescricao-medica-unidade',
    templateUrl: './prescricao-medica-unidade.component.html',
    styleUrls: ['./prescricao-medica-unidade.component.css'],
})
export class PrescricaoMedicaUnidadeComponent implements OnInit {
    @Input() precricaoEnfermagem: FormGroup;
    constructor() {}

    ngOnInit(): void {}
}
