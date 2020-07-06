import {
    IInformacaoTransferenciaDTO,
    InformacaoTransferenciaDTO,
} from './informacao-transferencia';
import { IItemTransferencia, ItemTransferencia } from './item-transferencia';

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
        public itens?: ItemTransferencia[],
    ) {}
}
