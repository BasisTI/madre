import { Router } from '@angular/router';
import { DatatableClickEvent } from '@nuvem/primeng-components';
import { SolicitacaoDeInternacaoService } from '@internacao/services/solicitacao-de-internacao.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-solicitacoes-de-internacao',
    templateUrl: './solicitacoes-de-internacao.component.html',
    styleUrls: ['./solicitacoes-de-internacao.component.scss'],
})
export class SolicitacoesDeInternacaoComponent implements OnInit {
    public api: string;

    constructor(
        private solicitacaoDeInternacaoService: SolicitacaoDeInternacaoService,
        private router: Router,
    ) {}

    ngOnInit(): void {
        this.api = this.solicitacaoDeInternacaoService.getApi();
    }

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
