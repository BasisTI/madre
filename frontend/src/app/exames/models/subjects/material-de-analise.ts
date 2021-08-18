
export interface IMaterialDeAnalise {
    id?: number;
    nome?: string;
    ativo?: boolean;
    coletavel?: boolean;
    exigeInformacao?: boolean;
    urina?: boolean;
}

export class MaterialDeAnalise implements IMaterialDeAnalise {
    public id?: number;
    public nome?: string;
    public ativo?: boolean;
    public coletavel?: boolean;
    public exigeInformacao?: boolean;
    public urina?: boolean;
}