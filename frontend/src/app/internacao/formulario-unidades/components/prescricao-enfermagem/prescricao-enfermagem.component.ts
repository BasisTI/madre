import { OPCOES_DE_UNIDADE_TEMPO } from './../../models/dropwdowns/types/opcoes-de-unidade-tempo';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-prescricao-enfermagem',
    templateUrl: './prescricao-enfermagem.component.html',
    styleUrls: ['./prescricao-enfermagem.component.css'],
})
export class PrescricaoEnfermagemComponent implements OnInit {
    @Input() precricaoEnfermagem: FormGroup;
    opcoesDeTempoDeUnidade = OPCOES_DE_UNIDADE_TEMPO;
    constructor() {}

    ngOnInit(): void {}
}
