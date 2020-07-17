export interface IItemTransferencia {
    id?: number;
    quantidadeEnviada?: number;
    materialId?: number;
}

export class ItemTransferencia implements IItemTransferencia {
    constructor(
        public id?: number,
        public quantidadeEnviada?: number,
        public materialId?: number,
    ) {}
}
