import { IitemSolicitacaoExame } from "./item-solicitacao-exame";

export class SolicitacaoExame {
    constructor(
        public id?: number,
        public infoClinica?: string,
        public usoAntimicrobianos24h?: boolean,
        public pedidoPrimeiroExame?: boolean,
        public itemSolicitacao?: IitemSolicitacaoExame[]
    ) {}
}
