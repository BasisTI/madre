import { SolicitacaoDeInternacaoService } from '@internacao/services/solicitacao-de-internacao.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BreadcrumbService } from '@breadcrumb/breadcrumb.service';
import { PrioridadeDropdown } from '@internacao/models/dropdowns/prioridades.dropdown';
import { ptBR } from '@shared/p-calendar.config';

@Component({
    selector: 'app-solicitacao-de-internacao',
    templateUrl: './solicitacao-de-internacao.component.html',
    styleUrls: ['./solicitacao-de-internacao.component.scss'],
})
export class SolicitacaoDeInternacaoComponent implements OnInit, OnDestroy {
    public prioridadeDropdown = PrioridadeDropdown;
    public pCalendarConfig = {
        localidade: ptBR,
        dataMinima: new Date(),
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yy',
    };

    public formGroup = this.fb.group({
        prontuario: this.fb.control({ value: '', disabled: true }, Validators.required),
        nomeDoPaciente: this.fb.control({ value: '', disabled: true }, Validators.required),
        dataProvavelDaInternacao: ['', Validators.required],
        dataProvavelDaCirurgia: [''],
        prioridade: ['', Validators.required],
        especialidade: ['', Validators.required],
        equipe: this.fb.control({ value: '', disabled: false }, Validators.required),
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
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    solicitarInternacao(): void {
        this.solicitacaoDeInternacaoService
            .solicitarInternacao(this.formGroup.value)
            .subscribe((resposta) => {
                console.log(resposta);
            });
    }
}
