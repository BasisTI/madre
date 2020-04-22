import { Component, Input } from '@angular/core';

import { FormGroup } from '@angular/forms';

import * as moment from 'moment';

import { ptBR } from '../../../shared/calendar.pt-br.locale';
import { RacaService } from './raca.service';
import { EtniaService } from './etnia.service';
import { NacionalidadeService } from './nacionalidade.service';
import { EstadoCivilService } from './estado-civil.service';
import { NaturalidadeService } from './naturalidade.service';
import { OcupacaoService } from './ocupacao.service';
import { ReligiaoService } from './religiao.service';

import { OPCOES_DE_SEXO } from '../../models/dropdowns/opcoes-de-sexo';
import { OPCOES_DE_GRAU_DE_INSTRUCAO } from '../../models/dropdowns/opcoes-de-grau-de-instrucao';

@Component({
    selector: 'app-dados-pessoais',
    templateUrl: './dados-pessoais.component.html',
    styleUrls: ['./dados-pessoais.component.scss'],
})
export class DadosPessoaisComponent {
    @Input() dadosPessoais: FormGroup;
    opcoesDeSexo = OPCOES_DE_SEXO;
    opcoesDeGrauDeInstrucao = OPCOES_DE_GRAU_DE_INSTRUCAO;
    localizacao = ptBR;
    dataLimite = new Date();
    anosDisponiveis = `1900:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    idade = '';
    uf = '';

    constructor(
        public racaService: RacaService,
        public etniaService: EtniaService,
        public estadoCivilService: EstadoCivilService,
        public nacionalidadeService: NacionalidadeService,
        public naturalidadeService: NaturalidadeService,
        public ocupacaoService: OcupacaoService,
        public religiaoService: ReligiaoService,
    ) {}

    aoSelecionarDataDeNascimento() {
        const { dataDeNascimento } = this.dadosPessoais.value;

        if (dataDeNascimento) {
            const idade = moment().diff(moment(dataDeNascimento), 'years');

            if (idade === 0) {
                this.idade = 'Menos de 1 ano';
                return;
            }

            this.idade = String(idade);

            return;
        }

        this.idade = '';
    }
}
