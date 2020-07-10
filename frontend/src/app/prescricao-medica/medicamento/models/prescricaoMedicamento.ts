import { ItemPrescricaoMedicamento } from "./itemPrescricaoMedicamento";

export class PrescricaoMedicamento {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public dataPrescricao?: Date,
        public observacao?: string,
        public itens?: ItemPrescricaoMedicamento[]
    ) {}
}
