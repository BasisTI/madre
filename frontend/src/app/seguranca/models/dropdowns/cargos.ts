export interface ICargos {
    id?: number;
    descricao?: string;
}

export class Cargos implements ICargos {
    public id: number;
    public descricao: string;
}
