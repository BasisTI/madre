export interface ITipoDeReservaDeLeito {
    id?: number;
    nome?: string;
}

export class OrigemDaReservaDeLeito implements ITipoDeReservaDeLeito {
    public id?: number;
    public nome?: string;
}
