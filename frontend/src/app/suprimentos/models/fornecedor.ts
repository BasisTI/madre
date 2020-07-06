export interface IFornecedor {
    id?: number;
    cpfCnpj?: string;
    razaoSocial?: string;
    nomeFantasia?: string;
}

export class Fornecedor {
    constructor(
        public id?: number,
        public cpfCnpj?: string,
        public razaoSocial?: string,
        public nomeFantasia?: string,
    ) {}
}
