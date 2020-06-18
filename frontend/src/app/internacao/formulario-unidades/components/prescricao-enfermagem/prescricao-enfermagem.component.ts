import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-prescricao-enfermagem',
    templateUrl: './prescricao-enfermagem.component.html',
    styleUrls: ['./prescricao-enfermagem.component.css'],
})
export class PrescricaoEnfermagemComponent implements OnInit {
    @Input() precricaoEnfermagem: FormGroup;
    constructor() {}

    ngOnInit(): void {}
}
