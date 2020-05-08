import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { OPCOES_DE_TIPO_DE_CERTIDAO } from '../../../models/dropdowns/opcao-de-tipos-de-certidao';

@Component({
    selector: 'app-certidao',
    templateUrl: './certidao.component.html',
    styles: [
        `
            .ui-widget.read-only:disabled {
                opacity: 1;
                background-color: #dddddd;
            }

            div {
                margin: 3px;
            }
        `,
    ],
})
export class CertidaoComponent {
    @Input() certidao: FormGroup;
    opcoesDeTipoDeCertidao = OPCOES_DE_TIPO_DE_CERTIDAO;
    localizacao = CALENDAR_LOCALE;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;

    constructor(private fb: FormBuilder) {}
}
