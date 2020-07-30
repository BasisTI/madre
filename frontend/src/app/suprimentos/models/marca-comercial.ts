export interface IMarcaComercial {
    id?: number;
    descricao?: string;
}

export class MarcaComercial {
    constructor(public id?: number, public descricao?: string) {}
}
