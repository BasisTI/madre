export interface IPessoa {
    nome?: string;
    id?: number;
}

export class Pessoa implements IPessoa {
    public nome: string;
    public id: number;
}