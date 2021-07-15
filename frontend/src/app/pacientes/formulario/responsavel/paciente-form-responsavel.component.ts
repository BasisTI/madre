import { Component, Input } from "@angular/core";

import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';
import { GrauDeParentescoService } from "./grau-de-parentesco.service";
import { FormGroup } from "@angular/forms";

@Component({
    selector: 'paciente-form-responsavel',
    templateUrl: './paciente-form-responsavel.component.html',
    styleUrls: ['paciente-form-responsavel.component.css']
})
export class PacienteResponsavelFormComponent {
    @Input() formGroup: FormGroup;

    opcoesDeGrauDeParentesco = [OPCAO_SELECIONE];

    constructor(public grauDeParentescoService: GrauDeParentescoService) {
    }

    ngOnInit(): void {
        this.grauDeParentescoService.getListaDeGrausDeParentesco().subscribe((dados) => {
            this.opcoesDeGrauDeParentesco = [
                ...this.opcoesDeGrauDeParentesco,
                ...dados.map((opcao) => {
                    return {
                        label: opcao.valor,
                        value: opcao,
                    };
                }),
            ];
        });
    }
}
