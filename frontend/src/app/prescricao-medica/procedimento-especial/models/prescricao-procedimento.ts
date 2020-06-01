import { ItemPrescricaoProcedimento } from './item-prescricao-procedimento';

export class PrescricaoProcedimento {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public observacao?: string,
        public itemPrescricaoProcedimentoDTO?: ItemPrescricaoProcedimento[]
    ) {}
}
