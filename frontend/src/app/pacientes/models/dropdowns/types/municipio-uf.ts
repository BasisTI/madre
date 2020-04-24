interface UF {
    sigla: string;
    unidadeFederativa: string;
}

export class MunicipioUF {
    id: number;
    nome: string;
    uf: UF;
}
