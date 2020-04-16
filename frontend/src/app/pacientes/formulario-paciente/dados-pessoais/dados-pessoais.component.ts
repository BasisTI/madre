import { Component, Input, OnInit } from '@angular/core';

import { FormGroup } from '@angular/forms';

import * as moment from 'moment';

import { ptBR } from '../../../shared/calendar.pt-br.locale';
import { RacaService } from '../../services/raca.service';
import { EtniaService } from '../../services/etnia.service';
import { NacionalidadeService } from '../../services/nacionalidade.service';
import { EstadoCivilService } from '../../services/estado-civil.service';
import { NaturalidadeService } from '../../services/naturalidade.service';
import { OcupacaoService } from '../../services/ocupacao.service';
import { ReligiaoService } from '../../services/religiao.service';
import { OpcaoCombo } from '../../models/dropdowns/opcao-combo';

import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';
import { OPCOES_DE_SEXO } from '../../models/dropdowns/opcoes-de-sexo';
import { OPCOES_DE_GRAU_DE_INSTRUCAO } from '../../models/dropdowns/opcoes-de-grau-de-instrucao';

@Component({
    selector: 'app-dados-pessoais',
    templateUrl: './dados-pessoais.component.html',
    styleUrls: ['./dados-pessoais.component.scss'],
})
export class DadosPessoaisComponent implements OnInit {
    @Input() dadosPessoais: FormGroup;
    idade = '';
    uf = '';
    opcoesDeRaca: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeNacionalidade: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeNaturalidade: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeEtnia: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeEstadoCivil: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeOcupacao: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeReligiao: OpcaoCombo[] = [OPCAO_SELECIONE];
    opcoesDeSexo = OPCOES_DE_SEXO;
    opcoesDeGrauDeInstrucao = OPCOES_DE_GRAU_DE_INSTRUCAO;
    localizacao = ptBR;
    dataLimite = new Date();
    anosDisponiveis = `1900:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

    constructor(
        private racaService: RacaService,
        private etniaService: EtniaService,
        private estadoCivilService: EstadoCivilService,
        private nacionalidadeService: NacionalidadeService,
        private naturalidadeService: NaturalidadeService,
        private ocupacaoService: OcupacaoService,
        private religiaoService: ReligiaoService,
    ) {}

    ngOnInit(): void {
        this.preencherComboRaca();
        this.preencherComboEtnia();
        this.preencherComboEstadoCivil();
        this.preencherComboNacionalidade();
        this.preencherComboNaturalidade();
        this.preencherComboOcupacao();
        this.preencherComboReligiao();
    }

    preencherComboRaca() {
        this.racaService.getListaDeRaças().subscribe((dados) => {
            this.opcoesDeRaca = [
                ...this.opcoesDeRaca,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    preencherComboEtnia() {
        this.etniaService.getListaDeEtnias().subscribe((dados) => {
            this.opcoesDeEtnia = [
                ...this.opcoesDeEtnia,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    preencherComboEstadoCivil() {
        this.estadoCivilService.getListaDeEstadoCivis().subscribe((dados) => {
            this.opcoesDeEstadoCivil = [
                ...this.opcoesDeEstadoCivil,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    preencherComboNacionalidade() {
        this.nacionalidadeService.getListaDeNacionalidades().subscribe((dados) => {
            this.opcoesDeNacionalidade = [
                ...this.opcoesDeNacionalidade,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    preencherComboNaturalidade() {
        this.naturalidadeService.getListaDeNaturalidades().subscribe((dados) => {
            this.opcoesDeNaturalidade = [
                ...this.opcoesDeNaturalidade,
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

    preencherComboOcupacao() {
        this.ocupacaoService.getListaDeOcupacoes().subscribe((dados) => {
            this.opcoesDeOcupacao = [
                ...this.opcoesDeOcupacao,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    preencherComboReligiao() {
        this.religiaoService.getListaDeReligioes().subscribe((dados) => {
            this.opcoesDeReligiao = [
                ...this.opcoesDeReligiao,
                ...dados.map(({ valor }) => {
                    return {
                        label: valor,
                        value: valor,
                    };
                }),
            ];
        });
    }

    aoSelecionarDataDeNascimento() {
        const { dataDeNascimento } = this.dadosPessoais.value;

        if (dataDeNascimento) {
            this.idade = String(moment().diff(moment(dataDeNascimento), 'years'));
            return;
        }

        this.idade = '';
    }

    aoSelecionarNaturalidade() {
        const { naturalidade } = this.dadosPessoais.value;

        if (naturalidade) {
            if (naturalidade.sigla) {
                this.uf = naturalidade.sigla;
                return;
            }
        }

        this.uf = '';
    }
}
