import { ItemPrescricaoDieta } from './itemPrescricaoDieta';
export class PrescricaoDieta {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public dataPrescricao?: Date,
        public observacao?: string,
        public itens?: ItemPrescricaoDieta[]
    ) { }
}
