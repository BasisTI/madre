export interface IPessoa {
    nome?: string;
    codigo?: number;
}

export class Pessoa implements IPessoa {
    public nome: string;
    public codigo: number;
}