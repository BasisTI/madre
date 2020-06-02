export class PreCadastroModel {
    constructor(
        public id?: number,

        public nome?: string,

        public nomeSocial?: string,

        public nomeDaMae?: string,

        public dataDeNascimento?: Date,

        public cartaoSus?: Number,

        public status?: boolean,
    ) {}
}
