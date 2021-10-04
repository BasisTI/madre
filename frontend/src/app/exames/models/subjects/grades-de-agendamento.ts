import * as momento from "moment";

export class GradeDeAgendamentoExame {
    constructor(
        public id?: number,
        public dataInicio?: Date,
        public dataFim?: Date,
        public horaInicio?: Date,
        public horaFim?: Date,
        public dia?: string,
        public numeroDeHorarios?: number,
        public duracao?: moment.Duration,
        public ativo?: boolean,
        public unidadeExecutoraId?: number,
        public responsavelId?: number,
        public exameId?: number,
        public salaId?: number
    ) { }
}