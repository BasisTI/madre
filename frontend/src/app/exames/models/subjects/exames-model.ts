export interface IExamModel {
    id?: number;
    nome?: string;
    material?: string;
    tipo?: String;
    unidade?: String;
    sigla?: string;
    composto?: string;
}

export class ExamModel implements IExamModel {
    public id?: number;
    public nome?: string;
    public material?: string;
    public tipo?: String;
    public unidade?: String;
    public sigla?: string;
    public composto?: string;
}