import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ICID, CID } from '../services/cid.service';
import { IEspecialidade, Especialidade } from '../services/especialidade.service';
import { ICRM } from '../services/crm.service';
import { IProcedimento, Procedimento } from '../services/procedimento.service';

export interface ISolicitacaoDeInternacao {
    prontuario?: string;
    nomeDoPaciente?: string;
    cidPrincipal?: ICID;
    cidSecundario?: ICID;
    dataProvavelDaInternacao?: Date;
    dataProvavelDaCirurgia?: Date;
    prioridade?: string;
    especialidade?: IEspecialidade;
    equipe?: string;
    crm?: ICRM;
    principaisSinaisESintomasClinicos?: string;
    condicoesQueJustificamInternacao?: string;
    principaisResultadosProvasDiagnosticas?: string;
    procedimento?: IProcedimento;
}

export class SolicitacaoDeInternacao implements ISolicitacaoDeInternacao {
    constructor(
        public prontuario?: string,
        public nomeDoPaciente?: string,
        public cidPrincipal?: CID,
        public cidSecundario?: CID,
        public dataProvavelDaInternacao?: Date,
        public dataProvavelDaCirurgia?: Date,
        public prioridade?: string,
        public especialidade?: Especialidade,
        public equipe?: string,
        public crm?: CID,
        public principaisSinaisESintomasClinicos?: string,
        public condicoesQueJustificamInternacao?: string,
        public principaisResultadosProvasDiagnosticas?: string,
        public procedimento?: Procedimento,
    ) {}
}

export interface ISolicitacaoDeInternacaoDTO {
    dataProvavelDaInternacao: Date;
    dataProvavelDaCirurgia?: Date;
    prioridade: string;
    principaisSinaisESintomasClinicos: string;
    condicoesQueJustificamInternacao: string;
    principaisResultadosProvasDiagnosticas: string;
    cidPrincipalId: number;
    cidSecundarioId: number;
    crmId: number;
    procedimentoId: number;
}

export class SolicitacaoDeInternacaoDTO implements ISolicitacaoDeInternacao {
    constructor(
        public dataProvavelDaInternacao: Date,
        public prioridade: string,
        public principaisSinaisESintomasClinicos: string,
        public condicoesQueJustificamInternacao: string,
        public principaisResultadosProvasDiagnosticas: string,
        public cidPrincipalId: number,
        public cidSecundarioId: number,
        public crmId: number,
        public procedimentoId: number,
        public dataProvavelDaCirurgia?: Date,
    ) {}
}

@Injectable({
    providedIn: 'root',
})
export class SolicitacaoDeInternacaoService {
    private readonly api = 'pacientes/api/solicitacoes-de-internacao';

    constructor(private httpClient: HttpClient) {}

    solicitarInternacao(solicitacao: SolicitacaoDeInternacao): void {
        const dto = new SolicitacaoDeInternacaoDTO(
            solicitacao.dataProvavelDaInternacao,
            solicitacao.prioridade,
            solicitacao.principaisSinaisESintomasClinicos,
            solicitacao.condicoesQueJustificamInternacao,
            solicitacao.principaisResultadosProvasDiagnosticas,
            solicitacao.cidPrincipal.id,
            solicitacao.cidSecundario.id,
            solicitacao.crm.id,
            solicitacao.procedimento.id,
            solicitacao.dataProvavelDaCirurgia,
        );

        this.httpClient.post<SolicitacaoDeInternacaoDTO>(this.api, dto).subscribe((response) => {
            console.log(response);
        });
    }
}
