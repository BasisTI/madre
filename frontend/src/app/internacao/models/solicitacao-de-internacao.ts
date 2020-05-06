import { CRM, ICRM } from '@internacao/models/crm';
import { Procedimento } from '@internacao/models/procedimento';
import { Equipe } from '@internacao/models/equipe';
import { CID } from '@internacao/models/cid';
import { IProcedimento } from '@internacao/models/procedimento';
import { IEquipe } from '@internacao/models/equipe';
import { ICID } from '@internacao/models/cid';

export interface ISolicitacaoDeInternacao {
    id?: number;
    dataProvavelDaInternacao: Date;
    dataProvavelDaCirurgia: Date;
    prioridade: string;
    principaisSinaisESintomasClinicos: string;
    condicoesQueJustificamInternacao: string;
    principaisResultadosProvasDiagnosticas: string;
    crm: ICRM;
    cidPrincipal: ICID;
    cidSecundario: ICID;
    equipe: IEquipe;
    procedimento: IProcedimento;
}

export class SolicitacaoDeInternacao implements ISolicitacaoDeInternacao {
    public id?: number;
    public dataProvavelDaInternacao: Date;
    public dataProvavelDaCirurgia: Date;
    public prioridade: string;
    public principaisSinaisESintomasClinicos: string;
    public condicoesQueJustificamInternacao: string;
    public principaisResultadosProvasDiagnosticas: string;
    public cidPrincipal: CID;
    public cidSecundario: CID;
    public equipe: Equipe;
    public procedimento: Procedimento;
    public crm: CRM;
}
