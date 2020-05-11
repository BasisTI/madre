import { Justificativa } from './../../../models/dropdowns/types/justificativa';
export class CartaoSUS {
    constructor(
        public id?: string,
        public numero?: string,
        public documentoDeReferencia?: string,
        public cartaoNacionalSaudeMae?: string,
        public dataDeEntradaNoBrasil?: Date,
        public dataDeNaturalizacao?: Date,
        public portaria?: string,
        public justificativaId?: number,
        public motivoDoCadastroId?: number,
    ) {}
}
