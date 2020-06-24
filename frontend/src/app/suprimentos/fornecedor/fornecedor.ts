export interface IFornecedor {
    id?: number;
    cnpj?: string;
    razaoSocial?: string;
    nomeFantasia?: string;
}

export class Fornecedor {
    constructor(
        public id?: number,
        public cnpj?: string,
        public razaoSocial?: string,
        public nomeFantasia?: string,
    ) {}
}
