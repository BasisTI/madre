export interface IAutorizacaoFornecimento {
    id?: number;
    numero?: number;
    complemento?: string;
    tipoItem?: string;
    fornecedorId?: number;
    fornecedorNome?: string;
}

export class AutorizacaoFornecimento {
    constructor(
        public id?: number,
        public numero?: number,
        public complemento?: string,
        public tipoItem?: string,
        public fornecedorId?: number,
        public fornecedorNome?: string,
    ) {}
}
