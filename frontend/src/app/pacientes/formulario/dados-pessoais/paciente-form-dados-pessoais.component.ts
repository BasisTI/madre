import { UfService } from './../municipio/uf.service';
import { Naturalidade } from './../../models/dropdowns/types/naturalidade';
import { UF } from './../../models/dropdowns/types/uf';
import { Component, Input } from "@angular/core";
import { FormGroup } from "@angular/forms";

import * as moment from 'moment';

import { OPCOES_DE_SEXO } from './../../models/dropdowns/opcoes-de-sexo';
import { OPCOES_DE_GRAU_DE_INSTRUCAO } from './../../models/dropdowns/opcoes-de-grau-de-instrucao';

import { RacaService } from "./raca.service";
import { EtniaService } from "./etnia.service";
import { EstadoCivilService } from "./estado-civil.service";
import { NacionalidadeService } from "./nacionalidade.service";
import { NaturalidadeService } from "./naturalidade.service";
import { OcupacaoService } from "./ocupacao.service";
import { ReligiaoService } from "./religiao.service";

@Component({
    selector: 'paciente-form-dados-pessoais',
    templateUrl: './paciente-form-dados-pessoais.component.html',
})
export class PacienteDadosPessoaisFormComponent {

    @Input()
    public formGroup: FormGroup;

    opcoesDeSexo = OPCOES_DE_SEXO;
    opcoesDeGrauDeInstrucao = OPCOES_DE_GRAU_DE_INSTRUCAO;
    idade = '';
    uf = '';

    ufs: UF[] = [];
    naturalidades: Naturalidade[] = [];

    constructor(
        public racaService: RacaService,
        public etniaService: EtniaService,
        public estadoCivilService: EstadoCivilService,
        public nacionalidadeService: NacionalidadeService,
        public ufService: UfService,
        public naturalidadeService: NaturalidadeService,
        public ocupacaoService: OcupacaoService,
        public religiaoService: ReligiaoService,
    ) {
    }

    ngOnInit() {
        this.ufService.getListaDeUF().subscribe((res) => (this.ufs = res));
    }

    aoSelecionarDataDeNascimento() {
        console.log("teste");
        const { dataDeNascimento } = this.formGroup.value;

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

    aoSelecionarUF() {
        this.formGroup.controls.naturalidadeId.setValue(null);
        this.naturalidadeService
            .getListaDeNaturalidades(this.formGroup.value.uf.id, '')
            .subscribe((res) => (this.naturalidades = res));
    }

    searchUnidade(event) {
        this.naturalidadeService
            .getListaDeNaturalidades(this.formGroup.value.uf.id, event.query)
            .subscribe((res) => {
                this.naturalidades = res;
            });
    }

}
