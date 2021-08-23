export class GradesDeAgendamento {
    constructor(
        public id?: number,
        public unidadeExecutoraId?: number,
        public responsavelId?: number,
        public ativo?: boolean,
        public exameGradeId?: number,
        public exameGradeNome?: string,
        public salaGradeId?: number,
        public salaGradeIdentificacaoDaSala?: string,
        public exameId?: number,
        public grupoAgendamentoExameId?: number
    ) { }
}