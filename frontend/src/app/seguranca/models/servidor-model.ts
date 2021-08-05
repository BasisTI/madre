export class ServidorModel {
    constructor(
        public id?: number,

        public codigo?: number,

        public matricula?: number,

        public codigoStarh?: number,

        public inicioDoVinculo?: Date,

        public fimDoVinculo?: Date,

        public situacao?: boolean,

        public situacaoDoServidor?: string,

        public centroDeAtividadeIdLotacao?: number,

        public centroDeAtividadeIdAtuacao?: number,

        public ocupacao?: string,

        public cargaHoraria?: string,

        public tipoDeRemuneracao?: string,

        public idade?: string,

        public tempoDeContrato?: string,

        public funcaoDoCracha?: string,

        public chefeDoCentroDeAtividade?: string,

        public vinculoId?: number,

        public vinculoDescricao?: string,

        public pessoaId?: number,

        public pessoaCodigo?: string,

        public ramalId?: number,

        public ramalNumero?: string,

        public usuarioId?: number,

        public usuarioLogin?: string,

    ) {}
}
