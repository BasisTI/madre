export interface IHospital {
    id?: number;
    nome?: string;
}

export class Hospital implements IHospital {
    public id: number;
    public nome: string;
}
