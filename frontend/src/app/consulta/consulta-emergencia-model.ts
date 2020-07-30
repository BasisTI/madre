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
    profissional: number;
    clinicaCentralId: number;
    observacao: string;
    justificativa: string;
    condicaoDeAtendimentoId: number;
    formaDeAgendamentoId: number;
    pacienteId: number;
    gradesDiponiveis: boolean;
}
