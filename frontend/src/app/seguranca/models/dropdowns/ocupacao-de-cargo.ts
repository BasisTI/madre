export interface IOcupacaoDeCargo {
    id?: number;
    descricao?: string;
}

export class OcupacaoDeCargo implements IOcupacaoDeCargo {
    constructor(public id?: number, public descricao?: string) {}
}
