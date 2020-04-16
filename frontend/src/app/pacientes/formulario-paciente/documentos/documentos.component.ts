import { ptBR } from './../../../shared/calendar.pt-br.locale';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { OrgaoEmissorService } from '../../services/orgao-emissor.service';
import { UfService } from '../../services/uf.service';
import { OpcaoCombo } from '../../models/dropdowns/opcao-combo';
import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';

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
export class DocumentosComponent implements OnInit {
    @Input() documentos: FormGroup;
    opcoesDeUF: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeOrgaoEmissor: OpcaoCombo[] = [OPCAO_SELECIONE];
    localizacao = ptBR;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;
    validade = ptBR;
    yearValidade = '2010:2030';

    constructor(
        private fb: FormBuilder,
        private orgaoEmissorService: OrgaoEmissorService,
        private ufService: UfService,
    ) {}

    ngOnInit(): void {
        this.preencherComboUF();
        this.preencherComboOrgaoEmissor();
    }

    preencherComboOrgaoEmissor() {
        this.orgaoEmissorService.getListaDeOrgaosEmissores().subscribe((dados) => {
            this.opcoesDeOrgaoEmissor = [
                ...this.opcoesDeOrgaoEmissor,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    preencherComboUF() {
        this.ufService.getListaDeUF().subscribe((dados) => {
            this.opcoesDeUF = [
                ...this.opcoesDeUF,
                ...dados.map(({ sigla }) => {
                    return {
                        label: sigla,
                        value: sigla,
                    };
                }),
            ];
        });
    }
}
