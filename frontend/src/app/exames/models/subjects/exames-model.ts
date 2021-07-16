export interface IExamModel {
    id?: number;
    nome?: string;
    nomeusual?: string;
    sigla?: string;
    materialExameId?: number;
    material?: string;
    amostraExameId?: number;
    amostraExameNome?: string;
}

export class ExamModel implements IExamModel {
    public id: number;
    public nome: string;
    public nomeusual: string;
    public sigla: string;
    public materialExameId: number;
    public material: string;
    public amostraExameId: number;
    public amostraExameNome: string;

}