export interface IMaterial {
    id?: number;
    nome?: string;
    codigo?: number;
}

export class Material {
    constructor(public id?: number, public nome?: string, public codigo?: number) {}
}