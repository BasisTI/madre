import { ChartModule } from 'primeng/chart';
import { Component, Input } from '@angular/core';

import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

import { ptBR } from '../../../shared/calendar.pt-br.locale';
import { strict } from 'assert';

@Component({
    selector: 'app-cartao-sus',
    templateUrl: './cartao-sus.component.html',
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
export class CartaoSusComponent {
    @Input() cartaoSUS: FormGroup;

    listaAusenciaCns = [
        { label: 'Selecione' },
        { label: 'Indivíduo acidentado grave', value: 'cns1407867196'[''] },
        { label: 'Indivíduo psiquiátrico encontrado em via pública', value: 'cns1049140526'[''] },
        {
            label: 'Indivíduo com problema neurológico grave ou comatoso',
            value: 'cns2064078612'[''],
        },
        {
            label: 'Indivíduo incapacitado por motivos sociais e/ou culturais',
            value: 'cns518398246'[''],
        },
        { label: 'Indivíduo doador de órgãos falecido', value: 'cns378164643'[''] },
    ];
    listaDocReferencia = [
        { label: 'Selecione' },
        { label: 'APAC', value: 'a1' },
        { label: 'AIH', value: 'a2' },
    ];

    listaMotivoCadastro = [
        { label: 'Selecione' },
        { label: 'Tratatamento Renal Substitutivo', value: 'tratRenal' },
        { label: 'Recém Nato', value: 'recemNato' },
        { label: 'Gestante', value: 'gestante' },
        { label: 'Hanseníase', value: 'hanseniase' },
        { label: 'Programa de Volta para Casa', value: 'voltaParaCasa' },
        { label: 'Estabelecimento Prisional', value: 'estabPrisional' },
        { label: 'Medicamento Excepcional', value: 'medicExcepc' },
        { label: 'Radioterapia', value: ' radio' },
        { label: 'Quimioterapia', value: 'quimio' },
        { label: 'Acompanhamento Pós-transplante', value: 'posTransplante' },
        { label: 'Contagem de Linfócitos T CD4/CD8', value: 'tCd4Cd8' },
        { label: 'Quantificação Carga Viral HIV', value: 'quantHiv' },
        { label: 'Demais proc. que exigem autorização prévia', value: 'autPrevia' },
        { label: 'Cirurgia Eletivas de Transplante', value: 'cirurgiaTranplante' },
        { label: 'Demais Cirurgias Eletivas', value: 'tuberculos' },
        { label: 'Tuberculose', value: 'tuberculose' },
        { label: 'Outros', value: 'outros' },
    ];

    localizacao = ptBR;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;

    constructor(private fb: FormBuilder) {}
}
