import { ptBR } from './../../../shared/calendar.pt-br.locale';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

@Component({
    selector: 'app-documentos',
    templateUrl: './documentos.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class DocumentosComponent {
    @Input() documentos: FormGroup;

    listaDeOrgaos = [
        { label: 'Selecione', value: null },
        { label: 'SSP-Secretária de Segurança Pública', value: 'Secretária de Segurança Pública' },
        { label: 'Policia Federal', value: 'Policia Federal' },
        { label: 'Ministério do Exército', value: 'Ministério do Exército' },
        { label: 'Ministério da Marinha', value: 'Ministério da Marinha' },
        {
            label: 'Conselho Regional de Contabilidade',
            value: 'Conselho Regional de Contabilidade',
        },
        { label: 'Conselho Regional de Enfermagem', value: 'Conselho de Enfermagem' },
    ];

    listaDeUF = [
        { label: 'Selecione', value: null },
        { label: 'AC - ACRE', value: 'ACRE' },
        { label: 'AL - ALAGOAS', value: 'ALAGOAS' },
        { label: 'AM - AMAZONAS', value: 'AMAZONAS' },
        { label: 'BA - BAHIA', value: 'BAHIA' },
        { label: 'CE - CEARA', value: 'CEARA' },
        { label: 'DF - DISTRITO FEDERAL', value: 'DISTRITO FEDERAL' },
    ];

    localizacao = ptBR;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;

    validade = ptBR;
    yearValidade = '2010:2030';

    constructor(private fb: FormBuilder) {}
}
