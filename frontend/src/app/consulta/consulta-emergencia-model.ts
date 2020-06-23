export interface ConsultaEmergenciaModel {
    numeroConsulta: string;
    dataHoraDaConsulta: Date;
    grade: string;
    nome: string;
    especialidade: string;
    profissional: string;
    clinicaCentralId: string;
    observacao: string;
    justificativa: string;
    condicaoDeAtendimentoId: string;
    formaDeAgendamentoId: string;
    pacienteId: {
        nome: string;
        prontuario: string;
        convenio: string;
    };
}
