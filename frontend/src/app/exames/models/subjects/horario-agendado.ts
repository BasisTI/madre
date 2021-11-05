export class HorarioExame {
    constructor(
        public id?: number,
        public horaInicio?: Date,
        public horaFim?: Date,
        public livre?: boolean,
        public ativo?: boolean,
        public exclusivo?: boolean,
        public tipoDeMarcacaoId?: number,
        public gradeAgendamentoExameId?: number
    ) { }
}