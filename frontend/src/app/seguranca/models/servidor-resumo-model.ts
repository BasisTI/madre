export interface IServidor {
    id?: number;
    matricula?: string;
    vinculoId?: number;
    pessoaId?: number;
    pessoaNome?: number;
}

export class Servidor implements IServidor {
    public id: number;
    public matricula: string;
    public vinculoId: number;
    public pessoaId: number;
    public pessoaNome: number;
}