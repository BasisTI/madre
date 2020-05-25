import { ILeito, Leito } from './leito';

export interface IEventoCalendario {
    id?: number;
    dataDoLancamento?: Date;
    leito?: ILeito;
    title?: string;
    start?: Date;
    end?: Date;
}

export class EventoCalendario implements IEventoCalendario {
    public id: number;
    public dataDoLancamento: Date;
    public leito: Leito;
    public title: string;
    public start: Date;
    public end: Date;
}
