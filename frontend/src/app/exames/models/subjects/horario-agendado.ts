import { Time } from "@angular/common";

export class HorarioAgendado {
    constructor(
        public id?: number,
        public horaInicio?: Time,
        public horaFim?: Time,
        public numeroDeHorarios?: number,
        public dia?: string,
        public duracao?: Time,
        public ativo?: boolean,
        public exclusivo?: boolean,
        public horarioAgendadoId?: number,
        public horarioAgendadoDia?: string,
        public gradeDeAgendamentoId?: number
    ) { }
}