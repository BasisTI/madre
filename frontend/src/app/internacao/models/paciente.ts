export interface IPaciente {
    id?: number;
    nome?: string;
    nomeSocial?: string;
    dataDeNascimento?: Date;
    sexo?: string;
    nomeDaMae?: string;
    numeroCartaoSUS?: string;
}

export class Paciente implements IPaciente {
    public id?: number;
    public nome?: string;
    public nomeSocial?: string;
    public dataDeNascimento?: Date;
    public sexo?: string;
    public nomeDaMae?: string;
    public numeroCartaoSUS?: string;
}
