export class GraduacaoModel {
    constructor(
        public id?: string,
        
        public curso?: string,

        public instituicao?: string,

        public anoInicio?: Date,

        public anoFim?: Date,

        public situacao?: boolean,

        public semestre?: string,

        public nroRegConselho?: string,

       ) {}
}
