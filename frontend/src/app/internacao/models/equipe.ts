export interface IEquipe {
    id?: number;
    numeroDoConselho?: number;
    nome?: string;
    especialidadeId?: number;
}

export class Equipe implements IEquipe {
    public id: number;
    public numeroDoConselho: number;
    public nome: string;
    public especialidadeId: number;
}
