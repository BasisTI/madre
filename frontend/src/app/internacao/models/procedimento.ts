export interface IProcedimento {
    id?: number;
    codigo?: string;
    procedimento?: string;
}

export class Procedimento implements IProcedimento {
    public id?: number;
    public codigo?: string;
    public procedimento?: string;
}
