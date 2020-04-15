import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { OpcaoCombo } from '../../models/dropdowns/opcao-combo';
import { MunicipioService } from '../../services/municipio.service';
import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-endereco';

@Component({
    selector: 'app-endereco',
    templateUrl: './endereco.component.html',
    styleUrls: ['./endereco.component.scss'],
})
export class EnderecoComponent implements OnInit {
    @Input() enderecos: FormGroup;
    opcoesDeMunicipio: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_TELEFONE;
    uf = '';

    constructor(private fb: FormBuilder, private municipioService: MunicipioService) {}

    ngOnInit(): void {
        this.preencherComboMunicipio();
    }

    preencherComboMunicipio() {
        this.municipioService.getListaDeMunicipiosUF().subscribe((dados) => {
            this.opcoesDeMunicipio = [
                ...this.opcoesDeMunicipio,
                ...dados.map(({ nome, uf: { sigla } }) => {
                    return {
                        label: nome,
                        value: {
                            nome,
                            sigla,
                        },
                    };
                }),
            ];
        });
    }

    aoSelecionarMunicipio() {
        const { municipio } = this.enderecos.value;

        if (municipio) {
            if (municipio.sigla) {
                this.uf = municipio.sigla;
                return;
            }
        }

        this.uf = '';
    }
}
