import { Equipe } from './equipe.service';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ICID, CID } from './cid.service';
import { IEspecialidade, Especialidade } from './especialidade.service';
import { ICRM } from './crm.service';
import { IProcedimento, Procedimento } from './procedimento.service';

export interface ISolicitacaoDeInternacao {
    prontuario?: string;
    nomeDoPaciente?: string;
    cidPrincipal?: { valor: ICID };
    cidSecundario?: { valor: ICID };
    dataProvavelDaInternacao?: Date;
    dataProvavelDaCirurgia?: Date;
    prioridade?: string;
    especialidade?: IEspecialidade;
    equipe?: Equipe;
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
        public cidPrincipal?: { valor: CID },
        public cidSecundario?: { valor: CID },
        public dataProvavelDaInternacao?: Date,
        public dataProvavelDaCirurgia?: Date,
        public prioridade?: string,
        public especialidade?: Especialidade,
        public equipe?: Equipe,
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
    equipeId: number;
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
        public equipeId: number,
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
            solicitacao.cidPrincipal.valor.id,
            solicitacao.cidSecundario.valor.id,
            solicitacao.crm.id,
            solicitacao.procedimento.id,
            solicitacao.equipe.id,
            solicitacao.dataProvavelDaCirurgia,
        );

        this.httpClient.post<SolicitacaoDeInternacaoDTO>(this.api, dto).subscribe((response) => {
            console.log(response);
        });
    }
}
