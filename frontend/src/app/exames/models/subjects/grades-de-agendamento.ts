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
        public unidadeNome?: string,
        public responsavelId?: number,
        public responsavelNome?: string,
        public exameId?: number,
        public exameNome?: string,
        public salaId?: number,
        public salaNome?: string
    ) { }
}