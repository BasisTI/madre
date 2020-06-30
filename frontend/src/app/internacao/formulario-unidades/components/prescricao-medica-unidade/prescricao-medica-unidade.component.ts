import { OPCOES_DE_UNIDADE_TEMPO } from './../../models/dropwdowns/types/opcoes-de-unidade-tempo';
import { FormGroup } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-prescricao-medica-unidade',
    templateUrl: './prescricao-medica-unidade.component.html',
    styleUrls: ['./prescricao-medica-unidade.component.css'],
})
export class PrescricaoMedicaUnidadeComponent implements OnInit {
    @Input() precricaoMedica: FormGroup;
    opcoesDeTempoDeUnidade = OPCOES_DE_UNIDADE_TEMPO;
    constructor() {}

    ngOnInit(): void {}
}
