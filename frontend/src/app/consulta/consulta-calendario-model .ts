export interface IConsultaCalendarioModel {
    id: number;
    title: string;
    turno?: string;
    tipoPagador?: string;
    start?: Date;
}
export class ConsultaCalendarioModel implements IConsultaCalendarioModel {
    public id: number;
    public title: string;
    public turno: string;
    public tipoPagador: string;
    public start: Date;
    public url: string;
}
