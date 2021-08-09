export interface IPessoa {
    nome?: string;
    id?: number;
    dataDeNascimento?: Date;
}

export class Pessoa implements IPessoa {
    public nome: string;
    public id: number;
    public dataDeNascimento: Date;
}