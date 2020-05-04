import { SolicitacaoDeInternacao } from './../models/solicitacao-de-internacao';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SolicitacaoDeInternacaoService {
    private readonly resource = `${api}/solicitacoes-de-internacao`;

    constructor(private client: HttpClient) {}

    solicitarInternacao(solicitacao: SolicitacaoDeInternacao): Observable<any> {
        const solicitacaoDTO = {
            dataProvavelDaInternacao: solicitacao.dataProvavelDaInternacao,
            dataProvavelDaCirurgia: solicitacao.dataProvavelDaInternacao,
            prioridade: solicitacao.prioridade,
            principaisSinaisESintomasClinicos: solicitacao.principaisSinaisESintomasClinicos,
            condicoesQueJustificamInternacao: solicitacao.condicoesQueJustificamInternacao,
            principaisResultadosProvasDiagnosticas:
                solicitacao.principaisResultadosProvasDiagnosticas,
            cidPrincipalId: solicitacao.cidPrincipal.id,
            cidSecundarioId: solicitacao.cidSecundario.id,
            equipeId: solicitacao.equipe.id,
            crmId: solicitacao.crm.id,
            procedimentoId: solicitacao.procedimento.id,
        };

        return this.client.post(this.resource, solicitacaoDTO);
    }
}
