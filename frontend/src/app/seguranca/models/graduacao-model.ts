export class GraduacaoModel {
    constructor(
        public curso?: string,

        public instituicao?: string,

        public anoInicio?: Date,

        public anoFim?: Date,

        public situacaoDaGraduacao?: boolean,

        public semestre?: string,

        public nroRegConselho?: string,

       ) {}
}
