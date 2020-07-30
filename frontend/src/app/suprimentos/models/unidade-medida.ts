export interface IUnidadeMedida {
    id?: number;
    sigla?: string;
    descricao?: string;
}

export class UnidadeMedida {
    constructor(public id?: number, public sigla?: string, public descricao?: string) {}
}
