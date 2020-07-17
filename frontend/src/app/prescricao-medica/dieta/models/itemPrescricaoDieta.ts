import { TipoUnidadeDieta } from './tipoUnidadeDieta';
import { TipoItemDieta } from './tipoItemDieta';
import { TipoAprazamento } from '@prescricao-medica/medicamento/models/tipoAprazamento';
export class ItemPrescricaoDieta {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public bombaInfusao?: boolean,
        public observacao?: string,
        public tipoItemDietaId?: TipoItemDieta,
        public tipoAprazamentoId?: TipoAprazamento,
        public tipoUnidadeDietaId?: TipoUnidadeDieta
    ) { }
}
