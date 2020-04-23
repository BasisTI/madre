import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GrauDeParentescoService } from './grau-de-parentesco.service';
import { OPCAO_SELECIONE } from '../../../models/dropdowns/opcao-selecione';
@Component({
    selector: 'app-responsavel',
    templateUrl: './responsavel.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class ResponsavelComponent implements OnInit {
    @Input() responsavel: FormGroup;

    opcoesDeGrauDeParentesco = [OPCAO_SELECIONE];

    constructor(private fb: FormBuilder, public grauDeParentescoService: GrauDeParentescoService) {}

    ngOnInit(): void {
        this.grauDeParentescoService.getListaDeGrausDeParentesco().subscribe((dados) => {
            this.opcoesDeGrauDeParentesco = [
                ...this.opcoesDeGrauDeParentesco,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }
}
