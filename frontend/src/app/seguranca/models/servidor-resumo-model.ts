export interface IServidor {
    matricula?: string;
    codigo?: number;
    vinculoId?: number;
}

export class Servidor implements IServidor {
    public matricula: string;
    public codigo: number;
    public vinculoId: number;
}