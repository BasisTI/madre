export interface IRamal {
    numero?: string;
    codigo?: number;
}

export class Ramal implements IRamal {
    public numero: string;
    public codigo: number;
}