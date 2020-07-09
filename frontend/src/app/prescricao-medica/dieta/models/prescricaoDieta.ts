import { ItemPrescricaoDieta } from './itemPrescricaoDieta';
export class PrescricaoDieta {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public observacao?: string,
        public itens?: ItemPrescricaoDieta[]
    ) { }
}
