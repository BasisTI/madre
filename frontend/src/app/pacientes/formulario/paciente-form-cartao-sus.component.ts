import { Component, Input } from "@angular/core";
import { JustificativaService } from "./justificativa.service";
import { MotivoDoCadastroService } from "./motivo-do-cadastro.service";
import { FormGroup } from "@angular/forms";

import { OPCOES_DE_DOCUMENTO_DE_REFERENCIA } from '../models/dropdowns/opcoes-de-documento-de-referencia';

@Component({
    selector: 'paciente-form-cartao-sus',
    templateUrl: './paciente-form-cartao-sus.component.html',
})
export class PacienteCartaoSusFormComponent {

    @Input() 
    public formGroup: FormGroup;

    opcoesDeDocumentoDeReferencia = OPCOES_DE_DOCUMENTO_DE_REFERENCIA;

    constructor(
        public justificativaService: JustificativaService,
        public motivoDoCadastroService: MotivoDoCadastroService
    ) {
    }

}
