import { ExamModel } from "./exames-model";

export interface IGrupoModel {
    id?: number;
    nome?: string;
    codigo?: number;
    agendarEmConjunto?: Boolean;
    calcularOcupacao?: Boolean;
    ativo?: Boolean;
    exames?: ExamModel[];
}

export class GrupoModel implements IGrupoModel {
    public id?: number;
    public nome?: string;
    public codigo?: number;
    public agendarEmConjunto?: Boolean;
    public calcularOcupacao?: Boolean;
    public ativo?: Boolean;
    public exames?: ExamModel[];
}