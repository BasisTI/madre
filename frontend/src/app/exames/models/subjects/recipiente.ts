import { Anticoagulante } from "./anticoagulante";

export interface RecipienteI {
    id?: Number;
    nome?: String;
    anticoagulante?: Anticoagulante
    ativo?: Boolean;
}

export class Recipiente implements RecipienteI {
    public id?: Number;
    public nome?: String;
    public anticoagulante?: Anticoagulante;
    public ativo?: Boolean;
}