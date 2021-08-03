import { Component, Input } from "@angular/core";

import { OPCOES_DE_TIPO_DE_CERTIDAO } from '../../models/dropdowns/opcao-de-tipos-de-certidao';
import { FormGroup } from "@angular/forms";

@Component({
    selector: 'paciente-form-certidao',
    templateUrl: './paciente-form-certidao.component.html',
    styleUrls: ['paciente-form-certidao.component.css']
})
export class PacienteCertidaoFormComponent {

    @Input() 
    public formGroup: FormGroup;

    opcoesDeTipoDeCertidao = OPCOES_DE_TIPO_DE_CERTIDAO;

}
