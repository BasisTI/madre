export interface IGrupoMaterial {
    id?: number;
    descricao?: string;
}

export class GrupoMaterial implements IGrupoMaterial {
    constructor(public id?: number, public descricao?: string) {}
}
