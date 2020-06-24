export interface IDocumentoFiscalEntrada {
    id?: number;
    numeroDocumento?: number;
    serie?: string;
    notaEmpenho?: string;
    cpfCnpj?: string;
    dataGeracao?: Date;
    dataEmissao?: Date;
    dataEntrada?: Date;
    dataVencimento?: Date;
    valorTotal?: number;
    tipoDocumento?: string;
    tipoDocumentoFiscal?: string;
    observacao?: string;
    fornecedorId?: number;
}

export class DocumentoFiscalEntrada {
    constructor(
        public id?: number,
        public numeroDocumento?: number,
        public serie?: string,
        public notaEmpenho?: string,
        public cpfCnpj?: string,
        public dataGeracao?: Date,
        public dataEmissao?: Date,
        public dataEntrada?: Date,
        public dataVencimento?: Date,
        public valorTotal?: number,
        public tipoDocumento?: string,
        public tipoDocumentoFiscal?: string,
        public observacao?: string,
        public fornecedorId?: number,
    ) {}
}
