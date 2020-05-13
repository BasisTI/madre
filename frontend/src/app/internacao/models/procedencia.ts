export interface IProcedencia {
    id?: number;
    descricao?: string;
}

export class Procedencia implements IProcedencia {
    public id: number;
    public descricao: string;
}
