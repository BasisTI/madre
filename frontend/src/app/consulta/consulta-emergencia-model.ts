export interface ConsultaEmergenciaModel {
    numeroConsulta: number;
    dataHoraDaConsulta: Date;
    grade: number;
    prontuario: string;
    nome: string;
    numeroDeSala: string;
    turno: string;
    tipoPagador: string;
    especialidade: string;
    profissional: string;
    clinicaCentralId: number;
    observacao: string;
    justificativa: string;
    condicaoDeAtendimentoId: number;
    formaDeAgendamentoId: number;
    pacienteId: number;
    gradesDiponiveis: boolean;
}

export class ConsultaEmergencia {
    public id?: number;
    public url?: string;
    public numeroConsulta?: number;
    public dataHoraDaConsulta?: Date;
    public grade?: number;
    public prontuario?: string;
    public nome?: string;
    public numeroDeSala?: string;
    public turno?: string;
    public tipoPagador?: string;
    public clinicaCentralId?: number;
    public observacao?: string;
    public justificativa?: string;
    public condicaoDeAtendimentoId?: number;
    public formaDeAgendamentoId: number;
    public pacienteId?: number;
    public gradesDiponiveis?: boolean;
    public especialidade: string;
    public profissional:  string;
 }
 

