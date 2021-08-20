export interface IRamal {
    numero?: string;
    id?: number;
}

export class Ramal implements IRamal {
    public numero: string;
    public id: number;
}