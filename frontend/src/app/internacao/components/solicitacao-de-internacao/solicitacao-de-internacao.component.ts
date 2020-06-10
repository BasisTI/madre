import { ActivatedRoute, Router } from '@angular/router';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Especialidade } from '@internacao/models/especialidade';
import { PrioridadeDropdown } from '@internacao/models/dropdowns/prioridades.dropdown';
import { SolicitacaoDeInternacao } from '@internacao/models/solicitacao-de-internacao';
import { SolicitacaoDeInternacaoService } from '@internacao/services/solicitacao-de-internacao.service';

@Component({
    selector: 'app-solicitacao-de-internacao',
    templateUrl: './solicitacao-de-internacao.component.html',
    styleUrls: ['./solicitacao-de-internacao.component.scss'],
})
export class SolicitacaoDeInternacaoComponent implements OnInit, OnDestroy {
    public pacienteId: number;
    public prioridadeDropdown = PrioridadeDropdown;
    public pCalendarConfig = {
        localidade: CALENDAR_LOCALE,
        dataMinima: new Date(),
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yy',
    };

    public formGroup = this.fb.group({
        // prontuario: this.fb.control({ value: '', disabled: true }, Validators.required),
        // nomeDoPaciente: this.fb.control({ value: '', disabled: true }, Validators.required),
        dataProvavelDaInternacao: ['', Validators.required],
        dataProvavelDaCirurgia: [''],
        prioridade: ['', Validators.required],
        especialidade: [null, Validators.required],
        equipe: this.fb.control({ value: null, disabled: true }, Validators.required),
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
        private route: ActivatedRoute,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Solicitação de Internação',
                routerLink: 'solicitacao-de-internacao',
            },
        ]);

        this.pacienteId = Number(this.route.snapshot.params['id']);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    solicitarInternacao(): void {
        const solicitacao: SolicitacaoDeInternacao = {
            ...this.formGroup.value,
            pacienteId: this.pacienteId,
        };

        this.solicitacaoDeInternacaoService
            .solicitarInternacao(solicitacao)
            .subscribe((resposta) => {
                console.log(resposta);
            });
    }

    aoSelecionarEspecialidade(especialidade: Especialidade): void {
        if (especialidade) {
            this.formGroup.get('equipe').enable();
            return;
        }

        this.formGroup.get('equipe').disable();
    }
}
