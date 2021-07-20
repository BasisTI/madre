export interface IUnidadeFuncional {
    nome?: string;
    id?: number;
}

export class UnidadeFuncional implements IUnidadeFuncional {
    public nome: string;
    public id: number;
}
