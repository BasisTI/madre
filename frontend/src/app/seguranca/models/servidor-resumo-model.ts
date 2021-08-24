export interface IServidor {
    matricula?: string;
    codigo?: number;
}

export class Servidor implements IServidor {
    public matricula: string;
    public codigo: number;
}