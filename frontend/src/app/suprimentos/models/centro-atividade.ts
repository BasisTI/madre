export interface ICentroAtividade {
    id?: number;
    descricao?: string;
}

export class CentroAtividade implements ICentroAtividade {
    constructor(public id?: number, public descricao?: string) {}
}
