export interface AnticoagulanteI {
    id?: Number;
    nome?: String;
    ativo?: Boolean;
}

export class Anticoagulante implements AnticoagulanteI {
    public id?: Number;
    public nome?: String;
    public ativo?: Boolean;
}