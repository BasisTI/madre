import { ItemPrescricaoMedicamento } from "./itemPrescricaoMedicamento";

export class PrescricaoMedicamento {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public observacao?: string,
        public itemPrescricaoMedicamentos?: ItemPrescricaoMedicamento[]
    ) {}
}
