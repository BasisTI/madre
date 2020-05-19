export class TriagemModel {
    constructor(
        public id?: number,

        public classificacaoDeRisco?: string,

        public pressaoArterial?: number,

        public frequenciaCardiaca?: number,
        //
        public temperatura?: number,

        public peso?: number,

        public sinaisSintomas?: string,

        public dataHoraDoAtendimento?: Date,

        public descricaoQueixa?: string,

        public vitimaDeAcidente?: boolean,

        public removidoDeAmbulancia?: boolean,

        public observacao?: string,
    ) {}
}
