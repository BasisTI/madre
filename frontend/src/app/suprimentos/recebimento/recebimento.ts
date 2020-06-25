import {
    IItemNotaRecebimento,
    ItemNotaRecebimento,
} from './../item-nota-recebimento/item-nota-recebimento';

export interface IRecebimento {
    id?: number;
    notaFiscalEntradaId?: number;
    autorizacaoFornecimentoId?: number;
    itensNotaRecebimento?: IItemNotaRecebimento;
}

export class Recebimento implements IRecebimento {
    constructor(
        public id?: number,
        public notaFiscalEntradaId?: number,
        public autorizacaoFornecimentoId?: number,
        public itensNotaRecebimento?: ItemNotaRecebimento,
    ) {}
}
