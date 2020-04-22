import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OPCOES_DE_PRIORIDADE } from '../models/dropdowns/opcoes-de-prioridade';
import { ptBR } from '../../shared/calendar.pt-br.locale';
import { ConfiguracaoParaCalendarioPrimeNG } from '../../shared/p-calendar.config';

@Component({
    selector: 'app-solicitacao-de-internacao',
    templateUrl: './solicitacao-de-internacao.component.html',
    styleUrls: ['./solicitacao-de-internacao.component.scss'],
})
export class SolicitacaoDeInternacaoComponent implements OnInit {
    opcoesDePrioridade = OPCOES_DE_PRIORIDADE;
    configuracaoParaCalendarios: ConfiguracaoParaCalendarioPrimeNG = {
        localidade: ptBR,
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
        condicoesQueJustificamAInternacao: ['', Validators.required],
        principaisResultadosProvasDiagnosticas: ['', Validators.required],
        procedimento: ['', Validators.required],
        cidPrincipal: ['', Validators.required],
        cidSecundario: [''],
    });

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Solicitação de Internação' },
        ]);
    }

    solicitarInternacao() {
        console.log(this.solicitacaoDeInternacao.value);
    }
}
