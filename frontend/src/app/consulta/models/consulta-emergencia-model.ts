export interface ConsultaEmergenciaModel {
    id: number;
    url: string;
    numeroConsulta: string;
    dataHoraDaConsulta: Date;
    grade: number;
    prontuario: string;
    nome: string;
    numeroSala: string;
    turno: string;
    tipoPagador: string;
    especialidade: string;
    profissional: number;
    clinicaCentralId: number;
    observacoes: string;
    justificativa: string;
    condicaoDeAtendimentoId: number;
    formaDeAgendamentoId: number;
    pacienteId: number;
    gradesDisponiveis: boolean;
}
