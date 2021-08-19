export class GradesDeAgendamento {
    constructor(
        public id?: number,
        public grade?: string,
        public unidadeExecutoraId?: number,
        public responsavelId?: number,
        public ativo?: boolean,
        public gradeDeAgendamentoId?: number,
        public salaId?: number,
        public exameId?: number,
        public grupoAgendamentoExameId?: number
    ) { }
}