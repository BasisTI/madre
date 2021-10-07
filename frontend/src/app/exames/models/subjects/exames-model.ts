import { Sinonimos } from "./sinonimos";

export class ExamModel {
    constructor(
        public id?: number,
        public nome?: string,
        public nomeUsual?: string,
        public sigla?: string,
        public ativo?: boolean,
        public impressao?: boolean,
        public consisteInterfaceamento?: boolean,
        public anexaDocumentos?: boolean,
        public materialExameId?: number,
        public materialExameNome?: string,
        public amostraExameId?: number,
        public amostraExameNome?: string,
        public sinonimos?: Sinonimos[],
    ) { }
}