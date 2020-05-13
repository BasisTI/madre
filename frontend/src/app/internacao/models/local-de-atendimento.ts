export interface ILocalDeAtendimento {
    id?: number;
    nome?: string;
}

export class LocalDeAtendimento implements ILocalDeAtendimento {
    public id: number;
    public nome: string;
}
