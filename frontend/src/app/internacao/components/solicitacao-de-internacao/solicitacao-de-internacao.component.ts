import { ActivatedRoute } from '@angular/router';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Especialidade } from '@internacao/models/especialidade';
import { PrioridadeDropdown } from '@internacao/models/dropdowns/prioridades.dropdown';
import { SolicitacaoDeInternacao } from '@internacao/models/solicitacao-de-internacao';
import { SolicitacaoDeInternacaoService } from '@internacao/services/solicitacao-de-internacao.service';
import { CID } from '@internacao/models/cid';
import { CidComponent } from '@internacao/components/cid/cid.component';

@Component({
    selector: 'app-solicitacao-de-internacao',
    templateUrl: './solicitacao-de-internacao.component.html',
    styleUrls: ['./solicitacao-de-internacao.component.scss'],
})
export class SolicitacaoDeInternacaoComponent implements OnInit, OnDestroy {
    @ViewChild('secundario') private cidSecundario: CidComponent;
    public pacienteId: number;
    public prioridadeDropdown = PrioridadeDropdown;
    public pCalendarConfig = {
        localidade: CALENDAR_LOCALE,
        dataMinima: new Date(),
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yy',
    };

    public formGroup = this.fb.group({
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
        cidSecundario: this.fb.control({ value: null, disabled: true }),
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private solicitacaoDeInternacaoService: SolicitacaoDeInternacaoService,
        private route: ActivatedRoute,
    ) {
    }

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

    public solicitarInternacao(): void {
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

    public aoSelecionarEspecialidade(especialidade: Especialidade): void {
        if (especialidade) {
            this.formGroup.get('equipe').enable();
            return;
        }

        this.formGroup.get('equipe').disable();
    }

    public aoSelecionarCidPrincipal(cid: CID): void {
        if (cid) {
            if (!cid.pai) {
                this.formGroup.get('cidSecundario').enable();
                this.cidSecundario.getFilhosPeloIdDoPai(cid.id);
            }
            return;
        }

        this.formGroup.get('cidSecundario').disable();
    }
}
