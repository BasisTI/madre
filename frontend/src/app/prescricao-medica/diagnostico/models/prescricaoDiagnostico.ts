import { ItemDiagnostico } from './itemDiagnostico';
export class PrescricaoDiagnostico {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public dataPrescricao?: Date,
        public observacao?: string,
        public itens?: ItemDiagnostico[]
    ) { }
}
