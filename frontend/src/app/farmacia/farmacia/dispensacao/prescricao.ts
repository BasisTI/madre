import { Data } from '@angular/router';

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
