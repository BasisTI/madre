import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';

export interface ICRM {
    id?: number;
    codigo?: string;
    nome?: string;
}

export class CRM implements ICRM {
    constructor(public id?: number, public codigo?, public nome?: string) {}
}

@Injectable({
    providedIn: 'root',
})
export class CrmService extends CrudServiceNuvem<number, CRM> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/crms?sort=nome', httpClient);
    }
}
