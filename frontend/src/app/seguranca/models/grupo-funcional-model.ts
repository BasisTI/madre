export interface IGrupoFuncional {
    descricao?: string;
    codigo?: number;
}

export class GrupoFuncional implements IGrupoFuncional {
    public descricao: string;
    public codigo: number;
}