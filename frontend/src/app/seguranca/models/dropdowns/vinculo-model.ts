export interface IVinculo {
    descricao?: string;
    id?: number;
}

export class Vinculo implements IVinculo {
    public descricao: string;
    public id: number;
}