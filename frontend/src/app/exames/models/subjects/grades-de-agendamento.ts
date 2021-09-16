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
        public grupoGradeId?: number,
        public grupoGradeNome?: string,
        public responsavelNome?: string,
        public unidadeExecutoraNome?: string
    ) { }
}