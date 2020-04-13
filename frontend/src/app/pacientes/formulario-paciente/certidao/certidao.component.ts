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
<<<<<<< HEAD
    @Input() certidao: FormGroup;
=======
    certidao: FormGroup = this.fb.group({
        registroDeNascimento: [''],
        tipoCertidao: [''],
        nomeDoCartorio: [''],
        livro: [''],
        folhas: [''],
        termo: [''],
        dataDeEmissao: [''],
        numeroDaDN: [''],
    });
>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000

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
