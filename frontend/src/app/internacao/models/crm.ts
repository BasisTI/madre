export interface ICRM {
    id?: number;
    codigo?: string;
    nome?: string;
}

export class CRM implements ICRM {
    public id?: number;
    public codigo?: string;
    public nome?: string;
}
