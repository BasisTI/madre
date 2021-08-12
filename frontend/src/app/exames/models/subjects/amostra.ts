export interface IAmostra {
    id?: number;
    nome?: string;
}

export class Amostra {
    constructor(public id?: number, public nome?: string) {}
}