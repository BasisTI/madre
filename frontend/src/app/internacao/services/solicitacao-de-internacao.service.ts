import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { SolicitacaoDeInternacao } from '@internacao/models/solicitacao-de-internacao';
import { SolicitacaoDeInternacaoDTO } from '@internacao/models/dtos/solicitacao-de-internacao.dto';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class SolicitacaoDeInternacaoService implements EntityService {
    private readonly resource = `${api}/solicitacoes-de-internacao`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getSolicitacaoPorId(id: number): Observable<SolicitacaoDeInternacaoDTO> {
        return this.client.get<SolicitacaoDeInternacaoDTO>(`${this.resource}/${id}`);
    }

    solicitarInternacao(
        solicitacao: SolicitacaoDeInternacao,
    ): Observable<SolicitacaoDeInternacaoDTO> {
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

        return this.client.post<SolicitacaoDeInternacaoDTO>(this.resource, solicitacaoDTO);
    }

    getApi(): string {
        return this.resource;
    }
}
