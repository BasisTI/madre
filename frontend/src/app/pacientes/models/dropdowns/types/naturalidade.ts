interface UF {
    sigla: string;
    unidadeFederativa: string;
}

export class Naturalidade {
    id: number;
    nome: string;
    uf: UF;

    constructor(id: number, nome: string, uf: UF) {}
}
