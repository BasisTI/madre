export interface IDarAltaAoPaciente {
    id?: number;
    nome?: string;
    prontuario?: number;
}

export class DarAltaAoPaciente implements IDarAltaAoPaciente {
    public id?: number;
    public nome?: string;
    public prontuario?: number;
}
