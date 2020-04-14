import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Raca } from './models/raca';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class RacaService extends CrudServiceNuvem<number, Raca> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/racas', httpClient);
    }
}
