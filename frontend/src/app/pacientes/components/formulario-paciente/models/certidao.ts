export class Certidao {
    constructor(
        public id?: number,
        public registroDeNascimento?: string,
        public tipoDaCertidao?: string,
        public nomeDoCartorio?: string,
        public livro?: string,
        public folhas?: string,
        public termo?: string,
        public dataDeEmissao?: Date,
        public numeroDaDeclaracaoDeNascimento?: string,
    ) {}
}
