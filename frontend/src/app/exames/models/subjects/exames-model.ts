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
        public material?: string,
        public amostraExameId?: number,
        public amostraExameNome?: string,
    ) { }
}