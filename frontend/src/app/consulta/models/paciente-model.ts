export interface IPacienteModel {
    id?: number;
    nome?: string;
    nomeSocial?: string;
    dataDeNascimento?: Date;
    sexo?: string;
    nomeDaMae?: string;
    numeroCartaoSUS?: string;
    prontuario?: number;
}

export class PacienteModel implements IPacienteModel {
    public id?: number;
    public nome?: string;
    public nomeSocial?: string;
    public dataDeNascimento?: Date;
    public sexo?: string;
    public nomeDaMae?: string;
    public numeroCartaoSUS?: string;
    public prontuario?: number;
}
