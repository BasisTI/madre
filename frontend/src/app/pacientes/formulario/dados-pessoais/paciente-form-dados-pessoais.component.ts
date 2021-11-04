import { UfService } from './uf.service';
import { Naturalidade } from './../../models/dropdowns/types/naturalidade';
import { UF } from './../../models/dropdowns/types/uf';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import * as moment from 'moment';

import { OPCOES_DE_SEXO } from './../../models/dropdowns/opcoes-de-sexo';
import { OPCOES_DE_GRAU_DE_INSTRUCAO } from './../../models/dropdowns/opcoes-de-grau-de-instrucao';

import { RacaService } from './raca.service';
import { EtniaService } from './etnia.service';
import { EstadoCivilService } from './estado-civil.service';
import { NacionalidadeService } from './nacionalidade.service';
import { NaturalidadeService } from './naturalidade.service';
import { OcupacaoService } from './ocupacao.service';
import { ReligiaoService } from './religiao.service';

@Component({
    selector: 'paciente-form-dados-pessoais',
    templateUrl: './paciente-form-dados-pessoais.component.html',
    styleUrls: ['paciente-form-dados-pessoais.component.css'],
})
export class PacienteDadosPessoaisFormComponent implements OnInit {
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
    ) {}

    ngOnInit() {
        this.ufService.getListaDeUF().subscribe((res) => (this.ufs = res));

        if (this.formGroup.get('etniaId').value != null) {
            this.etniaService.find(this.formGroup.get('etniaId').value).subscribe((res) => {
                this.formGroup.patchValue({ etniaId: res });
            });
        }

        if (this.formGroup.get('racaId').value != null) {
            this.racaService.find(this.formGroup.get('racaId').value).subscribe((res) => {
                this.formGroup.patchValue({ racaId: res });
            });
        }

        if (this.formGroup.get('estadoCivilId').value != null) {
            this.estadoCivilService
                .find(this.formGroup.get('estadoCivilId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ estadoCivilId: res });
                });
        }

        if (this.formGroup.get('horaDeNascimento').value != null) {
            var date = new Date(this.formGroup.get('horaDeNascimento').value);
            this.formGroup.patchValue({
                horaDeNascimento: new Date(this.formGroup.get('horaDeNascimento').value),
            });
        }

        if (this.formGroup.get('nacionalidadeId').value != null) {
            this.nacionalidadeService
                .find(this.formGroup.get('nacionalidadeId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ nacionalidadeId: res });
                });
        }

        if (this.formGroup.get('ufId').value != null) {
            this.ufService.find(this.formGroup.get('ufId').value).subscribe((res) => {
                this.formGroup.patchValue({ ufId: res });
            });
        }

        if (this.formGroup.get('naturalidadeId').value != null) {
            this.naturalidadeService
                .find(this.formGroup.get('naturalidadeId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ naturalidadeId: res });
                });
        }

        if (this.formGroup.get('ocupacaoId').value != null) {
            this.ocupacaoService.find(this.formGroup.get('ocupacaoId').value).subscribe((res) => {
                this.formGroup.patchValue({ ocupacaoId: res });
            });
        }

        if (this.formGroup.get('religiaoId').value != null) {
            this.religiaoService.find(this.formGroup.get('religiaoId').value).subscribe((res) => {
                this.formGroup.patchValue({ religiaoId: res });
            });
        }

        this.aoSelecionarDataDeNascimento();
    }

    aoSelecionarDataDeNascimento() {
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
            .getListaDeNaturalidades(this.formGroup.value.ufId.id, '')
            .subscribe((res) => (this.naturalidades = res));
    }

    searchUnidade(event) {
        this.naturalidadeService
            .getListaDeNaturalidades(this.formGroup.value.ufId.id, event.query)
            .subscribe((res) => {
                this.naturalidades = res;
            });
    }
}
