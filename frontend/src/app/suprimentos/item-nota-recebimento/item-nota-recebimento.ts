export interface IItemNotaRecebimento {
    id?: number;
    quantidadeReceber?: number;
    quantidadeConvertida?: number;
    valorTotal?: number;
    marcaComercialId?: number;
    recebimentoId?: number;
    materialId?: number;
    unidadeMedidaId?: number;
}

export class ItemNotaRecebimento {
    constructor(
        public id?: number,
        public quantidadeReceber?: number,
        public quantidadeConvertida?: number,
        public valorTotal?: number,
        public recebimentoId?: number,
        public marcaComercialId?: number,
        public materialId?: number,
        public unidadeMedidaId?: number,
    ) {}
}
