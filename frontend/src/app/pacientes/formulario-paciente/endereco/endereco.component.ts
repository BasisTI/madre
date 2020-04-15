import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { OpcaoCombo } from '../../models/dropdowns/opcao-combo';
import { UfService } from '../../services/uf.service';
import { MunicipioService } from '../../services/municipio.service';
import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-endereco';

@Component({
    selector: 'app-endereco',
    templateUrl: './endereco.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class EnderecoComponent implements OnInit {
    @Input() enderecos: FormGroup;
    opcoesDeUF: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeMunicipio: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_TELEFONE;

    constructor(
        private fb: FormBuilder,
        private ufService: UfService,
        private municipioService: MunicipioService,
    ) {}

    fakeData = [];
    adicionar() {
        this.fakeData.push(this.enderecos.value);
    }

    ngOnInit(): void {
        this.preencherComboUF();
        this.preencherComboMunicipio();
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

    preencherComboMunicipio() {
        this.municipioService.getListaDeMunicipios().subscribe((dados) => {
            this.opcoesDeMunicipio = [
                ...this.opcoesDeMunicipio,
                ...dados.map(({ nome }) => {
                    return {
                        label: nome,
                        value: nome,
                    };
                }),
            ];
        });
    }
}
