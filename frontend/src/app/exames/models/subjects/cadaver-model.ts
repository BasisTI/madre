export class CadaverModel {
    constructor(
        public nome?: string,
        public dataNascimento?: Date,
        public raca?: string,
        public grupoSanguineo?: string,
        public dataRemocao?: Date,
        public causaObito?: string,
        public realizadoPor?: string,
        public lidoPor?: string,
        public procedenciaId?: number,
        public retiradaId?: number,
        public codigoPlano?: number,
        public observacao?: string
    )  {}
}


