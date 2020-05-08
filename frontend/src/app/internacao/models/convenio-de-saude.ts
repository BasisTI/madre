export interface IConvenioDeSaude {
    id?: number;
    nome?: string;
}

export class ConvenioDeSaude implements IConvenioDeSaude {
    public id: number;
    public nome: string;
}
