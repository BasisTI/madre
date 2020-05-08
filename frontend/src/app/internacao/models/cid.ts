export interface ICID {
    id?: number;
    codigo?: string;
    descricao?: string;
    pai?: ICID;
}

export class CID implements ICID {
    public id: number;
    public codigo: string;
    public descricao: string;
    public pai: CID;
}
