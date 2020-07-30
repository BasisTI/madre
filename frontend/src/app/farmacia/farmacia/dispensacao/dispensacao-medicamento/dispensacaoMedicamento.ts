import { Medicamento } from './../../medicamentos/Medicamento';
// tslint:disable-next-line: no-empty-interface
export interface DispensacaoMedicamento {
    id: number;
    dispensacaoId: number;
    dispensado: boolean;
    idMedicamento: Medicamento;
}
