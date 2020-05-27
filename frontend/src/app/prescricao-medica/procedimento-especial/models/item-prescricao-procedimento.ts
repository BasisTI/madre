export class ItemPrescricaoProcedimento {
    constructor(
        public id?: number,
        public tipoProcedimento?: string,
        public quantidadeOrteseProtese?: number,
        public informacoes?: string,
        public justificativa?: string,
        public duracaoSolicitada?: number,
        public especiaisDiversosId?: number,
        public cirurgiasLeitoId?: number,
        public orteseProteseId?: number
    ) { }
}
