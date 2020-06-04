export interface ISolicitacaoDeInternacaoDTO {
    id?: number;
    pacienteId?: number;
    dataProvavelDaInternacao: Date;
    dataProvavelDaCirurgia: Date;
    prioridade: string;
    principaisSinaisESintomasClinicos: string;
    condicoesQueJustificamInternacao: string;
    principaisResultadosProvasDiagnosticas: string;
    cidPrincipalId: number;
    cidSecundarioId: number;
    equipeId: number;
    crmId: number;
    procedimentoId: number;
}

export class SolicitacaoDeInternacaoDTO implements ISolicitacaoDeInternacaoDTO {
    public id?: number;
    public pacienteId?: number;
    public dataProvavelDaInternacao: Date;
    public dataProvavelDaCirurgia: Date;
    public prioridade: string;
    public principaisSinaisESintomasClinicos: string;
    public condicoesQueJustificamInternacao: string;
    public principaisResultadosProvasDiagnosticas: string;
    public cidPrincipalId: number;
    public cidSecundarioId: number;
    public equipeId: number;
    public crmId: number;
    public procedimentoId: number;
}
