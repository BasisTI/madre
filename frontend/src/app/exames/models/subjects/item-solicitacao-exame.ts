export class ItemSolicitacaoExame {
    constructor(
        public id?: number,
        public itemSolicitacaoExameId?: number,
        public urgente?: boolean,
        public dataProgramada?: Date,
        public situacao?: string,
    ) {}
}
