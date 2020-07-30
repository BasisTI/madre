import { Motivo } from './../Motivo';
export interface Estorno {
    id: number;
    dispensacaoMedicamentosId: number;
    estornado: boolean;
    motivoId: Motivo;
}
