import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OPCOES_DE_PRIORIDADE } from '../models/dropdowns/opcoes-de-prioridade';
import { ptBR } from '../../shared/calendar.pt-br.locale';
import { ConfiguracaoParaCalendarioPrimeNG } from '../../shared/p-calendar.config';
import { EspecialidadeService, Especialidade } from '../services/especialidade.service';
import { CrmService } from '../services/crm.service';
import { ProcedimentoService } from '../services/procedimento.service';
import { CidService } from '../services/cid.service';
import { SolicitacaoDeInternacaoService } from './solicitacao-de-internacao.service';
import { EquipeService, Equipe } from '../services/equipe.service';
import { query } from '@angular/animations';

@Component({
    selector: 'app-solicitacao-de-internacao',
    templateUrl: './solicitacao-de-internacao.component.html',
    styleUrls: ['./solicitacao-de-internacao.component.scss'],
})
export class SolicitacaoDeInternacaoComponent implements OnInit, OnDestroy {
    equipes: Equipe[];
    especialidades: Especialidade[];
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
        public cidService: CidService,
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

    buscarEspecialidades(evento: { originalEvent: InputEvent; query: string }): void {
        this.especialidadeService
            .buscarEspecialidadePorNome(evento.query)
            .subscribe((especialidades) => {
                this.especialidades = especialidades;
            });
    }

    buscarEquipes(evento: { originalEvent: InputEvent; query: string }): void {
        const {
            especialidade: { id },
        } = this.solicitacaoDeInternacao.value;

        this.equipeService
            .buscarEquipePorEspecialidadeIdENome(id, evento.query)
            .subscribe((equipes) => {
                this.equipes = equipes;
            });
    }

    solicitarInternacao(): void {
        this.solicitacaoDeInternacaoService.solicitarInternacao(this.solicitacaoDeInternacao.value);
    }
}
