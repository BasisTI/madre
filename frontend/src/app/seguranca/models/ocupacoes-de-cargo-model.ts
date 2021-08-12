
export class OcupacoesDeCargoModel {
    constructor(
        public id?: number,

        public codigo?: number,

        public descricao?: string,

        public situacao?: boolean,

        public informarCbo?: boolean,

        public informarCns?: boolean,

        public cargoId?: number, 
    ) { }
}