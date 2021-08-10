export class ExamModel {
    constructor(
        public id?: number,
        public nome?: string,
        public nomeUsual?: string,
        public sigla?: string,
        public materialExameId?: number,
        public material?: string,
        public amostraExameId?: number,
        public amostraExameNome?: string,
    ) { }
}