export class ItemPrescricaoProcedimento {
    constructor(
        public id?: number,
        public tipoProcedimentoEspecial?: string,
        public quantidadeOrteseProtese?: number,
        public informacoes?: string,
        public justificativa?: string,
        public duracaoSolicitada?: number,
        public tipoProcedimentoId?: number,
    ) { }
}
