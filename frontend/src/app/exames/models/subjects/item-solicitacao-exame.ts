export class ItemSolicitacaoExame {
    constructor(
        public id?: number,
        public urgente?: boolean,
        public dataProgramada?: Date,
        public situacao?: string,
    ) {}
}
