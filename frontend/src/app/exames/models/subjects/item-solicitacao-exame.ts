import { ExamModel } from "./exames-model";

export interface IitemSolicitacaoExame {

    urgente?: boolean;
    dataProgramada?: Date;
    situacao?: string;
    itemSolicitacaoExameId?: number;
}
export class ItemSolicitacaoExame implements IitemSolicitacaoExame {
    
    public urgente: boolean;
    public dataProgramada: Date;
    public situacao: string;
    public itemSolicitacaoExameId: number;
}
