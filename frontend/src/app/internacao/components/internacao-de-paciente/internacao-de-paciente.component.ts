import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { ActivatedRoute } from '@angular/router';
import { Internacao } from '@internacao/models/internacao';
import { InternacaoDePacienteService } from '@internacao/services/internacao-de-paciente.service';
import { SolicitacaoDeInternacaoDTO } from '@internacao/models/dtos/solicitacao-de-internacao.dto';
import { SolicitacaoDeInternacaoService } from '@internacao/services/solicitacao-de-internacao.service';

@Component({
    selector: 'app-internacao-de-paciente',
    templateUrl: './internacao-de-paciente.component.html',
    styleUrls: ['./internacao-de-paciente.component.scss'],
})
export class InternacaoDePacienteComponent implements OnInit, OnDestroy {
    public solicitacao: SolicitacaoDeInternacaoDTO;

    public pCalendarConfig = {
        localidade: CALENDAR_LOCALE,
        dataMinima: new Date(),
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yy',
    };

    constructor(
        private fb: FormBuilder,
        private solicitacaoDeInternacaoService: SolicitacaoDeInternacaoService,
        private internacaoDePacienteService: InternacaoDePacienteService,
        private route: ActivatedRoute,
        private breadcrumbService: BreadcrumbService,
    ) {}

    public formGroup = this.fb.group({
        procedimento: this.fb.control({ value: '', disabled: true }, Validators.required),
        leito: ['', Validators.required],
        especialidade: ['', Validators.required],
        planoDeSaude: ['', Validators.required],
        convenioDeSaude: ['', Validators.required],
        caraterDaInternacao: ['', Validators.required],
        origemDaInternacao: ['', Validators.required],
        hospitalDeOrigem: ['', Validators.required],
        procedencia: ['', Validators.required],
        localDeAtendimento: ['', Validators.required],
        modalidadeAssistencial: ['', Validators.required],
        dataDaInternacao: ['', Validators.required],
        diferencaDeClasse: [false, Validators.required],
        solicitarProntuario: [false, Validators.required],
        justificativa: ['', Validators.required],
        crm: ['', Validators.required],
    });

    ngOnInit(): void {
        this.breadcrumbService.setItems([{ label: 'Internação' }, { label: 'Internar Paciente' }]);

        const id = this.route.snapshot.params['id'];
        this.solicitacaoDeInternacaoService.getSolicitacaoPorId(id).subscribe((solicitacao) => {
            this.solicitacao = solicitacao;
        });
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    internarPaciente() {
        const internacao: Internacao = {
            ...this.formGroup.value,
            pacienteId: this.solicitacao.id,
        };

        this.internacaoDePacienteService
            .internarPaciente(internacao, this.solicitacao)
            .subscribe((resposta: Internacao) => {
                console.log(resposta);
            });
    }
}
