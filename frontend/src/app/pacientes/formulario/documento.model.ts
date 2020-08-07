export class Documento {
    constructor(
        public id?: number,
        public numeroDaIdentidade?: string,
        public data?: Date,
        public cpf?: string,
        public pisPasep?: string,
        public cnh?: string,
        public validadeDaCnh?: Date,
        public documentosApresentados?: boolean,
        public orgaoEmissorId?: number,
        public ufId?: number,
    ) {}
}
