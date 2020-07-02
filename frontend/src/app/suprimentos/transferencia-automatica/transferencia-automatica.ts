import { IItemTransferencia } from '@suprimentos/item-transferencia/item-transferencia';

interface IInformacaoTransferenciaDTO {
    id?: number;
    ativa?: boolean;
    efetivada?: boolean;
    classificacaoMaterialId?: number;
    centroDeAtividadeId?: number;
}

class InformacaoTransferenciaDTO implements IInformacaoTransferenciaDTO {
    constructor(
        public id?: number,
        public ativa?: boolean,
        public efetivada?: boolean,
        public classificacaoMaterialId?: number,
        public centroDeAtividadeId?: number
    ) {
    }
}

export interface ITransferenciaAutomaticaDTO {
    id?: number;
    origemId?: number;
    destinoId?: number;
    informacaoTransferencia?: IInformacaoTransferenciaDTO;
    itens?: IItemTransferencia[];
}

export class TransferenciaAutomaticaDTO implements ITransferenciaAutomaticaDTO {
    constructor(
        public id?: number,
        public origemId?: number,
        public destinoId?: number,
        public informacaoTransferencia?: InformacaoTransferenciaDTO,
        public itens?: IItemTransferencia[]
    ) {
    }
}



