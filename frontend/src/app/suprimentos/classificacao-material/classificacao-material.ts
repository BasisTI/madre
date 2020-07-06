export interface IClassificacaoMaterial {
    id?: number;
    descricao?: string;
}

export class ClassificacaoMaterial implements IClassificacaoMaterial {
    constructor(public id?: number, public descricao?: string) {
    }
}
