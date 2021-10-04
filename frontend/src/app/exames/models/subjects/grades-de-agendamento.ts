import { Dia } from "./dia";

export class GradeDeAgendamentoExame {
    constructor(
        public id?: number,
        public dataInicio?: Date,
        public dataFim?: Date,
        public horaInicio?: Date,
        public horaFim?: Date,
        public dias?: Array<Dia>,
        public numeroDeHorarios?: number,
        public duracao?: moment.Duration,
        public ativo?: boolean,
        public unidadeExecutoraId?: number,
        public responsavelId?: number,
        public exameId?: number,
        public salaId?: number
    ) { }
}