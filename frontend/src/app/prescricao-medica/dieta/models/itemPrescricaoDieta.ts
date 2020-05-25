export class ItemPrescricaoDieta {
    constructor(
        public id?: number,
        public idPaciente?: number,
        public bombaInfusao?: boolean,
        public observacao?: string,
        public tipoItemDietaId?: number,
        public tipoAprazamentoId?: number,
        public tipoUnidadeDietaId?: number
    ) { }
}
