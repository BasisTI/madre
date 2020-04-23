import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';

export interface IProcedimento {
    id?: number;
    codigo?: string;
    procedimento?: string;
}

export class Procedimento implements IProcedimento {
    constructor(public id?: number, public codigo?, public procedimento?: string) {}
}

@Injectable({
    providedIn: 'root',
})
export class ProcedimentoService extends CrudServiceNuvem<number, Procedimento> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/procedimentos?sort=procedimento', httpClient);
    }
}
