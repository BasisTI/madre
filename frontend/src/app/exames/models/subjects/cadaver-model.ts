export class CadaverModel {
    constructor(
        public nome?: string,
        public dataNascimento?: number,
        public raca?: string,
        public grupoSanguineo?: string,
        public dataRemocao?: number,
        public causaObito?: string,
        public realizadoPor?: string,
        public lidoPor?: string,
        public procedenciaId?: number,
        public retiradaId?: number,
        public codigoConvenioId?: number,
        public observacao?: string
    )  {}
}


