import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import {
    FormBuilder,
    Validators,
    FormControl,
    AbstractControl,
    FormControlDirective,
} from '@angular/forms';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { OPCOES_DE_PRIORIDADE } from '../../models/dropdowns/opcoes-de-prioridade';
import { ConfiguracaoParaCalendarioPrimeNG } from '../../../shared/p-calendar.config';
import { EspecialidadeService, Especialidade } from './especialidade.service';
import { CrmService } from './crm.service';
import { ProcedimentoService } from './procedimento.service';
import { CidService } from './cid.service';
import { SolicitacaoDeInternacaoService } from './solicitacao-de-internacao.service';
import { EquipeService, Equipe } from './equipe.service';
import { Event } from '@angular/router';

@Component({
    selector: 'app-solicitacao-de-internacao',
    templateUrl: './solicitacao-de-internacao.component.html',
    styleUrls: ['./solicitacao-de-internacao.component.scss'],
})
export class SolicitacaoDeInternacaoComponent implements OnInit, OnDestroy {
    opcoesDePrioridade = OPCOES_DE_PRIORIDADE;
    configuracaoParaCalendarios: ConfiguracaoParaCalendarioPrimeNG = {
        localidade: CALENDAR_LOCALE,
        dataMinima: new Date(),
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yy',
    };
    solicitacaoDeInternacao = this.fb.group({
        prontuario: this.fb.control({ value: '', disabled: true }, Validators.required),
        nomeDoPaciente: this.fb.control({ value: '', disabled: true }, Validators.required),
        dataProvavelDaInternacao: ['', Validators.required],
        dataProvavelDaCirurgia: [''],
        prioridade: ['', Validators.required],
        especialidade: ['', Validators.required],
        equipe: this.fb.control({ value: '', disabled: true }, Validators.required),
        crm: ['', Validators.required],
        principaisSinaisESintomasClinicos: ['', Validators.required],
        condicoesQueJustificamInternacao: ['', Validators.required],
        principaisResultadosProvasDiagnosticas: ['', Validators.required],
        procedimento: ['', Validators.required],
        cidPrincipal: ['', Validators.required],
        cidSecundario: [''],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private solicitacaoDeInternacaoService: SolicitacaoDeInternacaoService,
        public especialidadeService: EspecialidadeService,
        public crmService: CrmService,
        public procedimentoService: ProcedimentoService,
        public cidPrincipalService: CidService,
        public cidSecundarioService: CidService,
        public equipeService: EquipeService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Solicitação de Internação' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    limparCampo(el: ElementRef) {
        const formControlName = el.nativeElement.getAttribute('formControlName');
        const formControl: AbstractControl = this.solicitacaoDeInternacao.get(formControlName);

        if (!formControl.value) {
            formControl.setValue(null);
        }
    }

    desabilitarCampo(campo: FormControl) {
        campo.disable();
    }

    habilitarCampoEquipe() {
        const { equipe, especialidade } = this.solicitacaoDeInternacao.controls;

        if (especialidade.valid) {
            equipe.enable();
        }
    }

    desabilitarCampoEquipe() {
        const { equipe, especialidade } = this.solicitacaoDeInternacao.controls;

        if (especialidade.invalid) {
            equipe.setValue(null);
            equipe.disable();
        }
    }

    solicitarInternacao(): void {
        this.solicitacaoDeInternacaoService.solicitarInternacao(this.solicitacaoDeInternacao.value);
    }
}
