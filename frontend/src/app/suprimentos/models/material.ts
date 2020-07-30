export interface IMaterial {
    id?: number;
    nome?: string;
    descricao?: string;
}

export class Material {
    constructor(public id?: number, public nome?: string, public descricao?: string) {}
}
