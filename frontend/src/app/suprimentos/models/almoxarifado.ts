export interface IAlmoxarifado {
    id?: number;
    descricao?: string;
}

export class Almoxarifado implements IAlmoxarifado {
    constructor(public id?: number, public descricao?: string) {}
}
