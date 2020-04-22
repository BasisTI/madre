import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';

export interface ICID {
    id?: number;
    codigo?: string;
    descricao?: string;
}

export class CID implements ICID {
    constructor(public id?: number, public codigo?, public descricao?: string) {}
}
@Injectable({
    providedIn: 'root',
})
export class CidService extends CrudServiceNuvem<number, CID> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/cids?sort=descricao', httpClient);
    }
}
