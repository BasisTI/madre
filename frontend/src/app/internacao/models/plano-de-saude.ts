export interface IPlanoDeSaude {
    id?: number;
    nome?: string;
}

export class PlanoDeSaude implements IPlanoDeSaude {
    public id: number;
    public nome: string;
}
