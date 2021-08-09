export interface IGrupoFuncional {
    descricao?: string;
    id?: number;
}

export class GrupoFuncional implements IGrupoFuncional {
    public descricao: string;
    public id: number;
}