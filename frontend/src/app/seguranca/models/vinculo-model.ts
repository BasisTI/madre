export interface IVinculo {
    descricao?: string;
    codigo?: number;
}

export class Vinculo implements IVinculo {
    public descricao: string;
    public codigo: number;
}