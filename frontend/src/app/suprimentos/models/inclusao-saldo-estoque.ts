export interface IInclusaoSaldoEstoqueDTO {
    materialId?: number;
    almoxarifadoId?: number;
    quantidade?: number;
    valorTotal?: number;
}

export class InclusaoSaldoEstoqueDTO implements IInclusaoSaldoEstoqueDTO {
    constructor(
        public materialId?: number,
        public almoxarifadoId?: number,
        public quantidade?: number,
        public valorTotal?: number,
    ) {}
}
