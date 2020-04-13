import { Component, Input } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';

import { ptBR } from '../../../shared/calendar.pt-br.locale';

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

    listaCertidao = [
        { label: 'Selecione' },
        { label: 'Nascimento', value: 'nascimento' },
        { label: 'Casamento', value: 'casamento' },
        { label: 'Separacao/Divorcio', value: 'separacaoDivorcio' },
        { label: 'Indigena', value: 'indigena' },
    ];

    localizacao = ptBR;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;

    constructor(private fb: FormBuilder) {}
}
