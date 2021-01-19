import { BreadcrumbService, DatatableClickEvent } from '@nuvem/primeng-components';
import { Component, OnDestroy, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { SolicitacaoDeInternacaoService } from '@internacao/services/solicitacao-de-internacao.service';

@Component({
    selector: 'app-solicitacoes-de-internacao',
    templateUrl: './solicitacoes-de-internacao.component.html',
    styleUrls: ['./solicitacoes-de-internacao.component.scss'],
})
export class SolicitacoesDeInternacaoComponent implements OnInit, OnDestroy {
    public api: string;
    public filtroNomeDoPaciente: string;

    constructor(
        private solicitacaoDeInternacaoService: SolicitacaoDeInternacaoService,
        private router: Router,
        private breadcrumbService: BreadcrumbService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Internar Paciente',
            },
        ]);

        this.api = this.solicitacaoDeInternacaoService.getApi();
    }

    ngOnDestroy(): void {}

    onButtonClick(evento: DatatableClickEvent): void {
        if (!evento.selection) {
            return;
        }

        switch (evento.button) {
            case 'create':
                this.router.navigate(['/internacao/internacao-de-paciente', evento.selection.id]);
                break;
        }
    }
}
