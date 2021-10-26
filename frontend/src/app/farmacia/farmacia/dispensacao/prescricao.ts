import { Data } from '@angular/router';
import { BaseEntity } from '@shared/base-entity';

export interface Prescricao {
    id: number;
    descricao: string;
    nome: string;
    farmacia: string;
    unidade: string;
    dataInicio: Date;
    dataFim: Date;
    local: string;
}

export class Prescricaos implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public nome?: string,
        public farmacia?: string,
        public unidade?: string,
        public dataInicio?: Date,
        public dataFim?: Date,
        public local?: string,
    ) {}
}
