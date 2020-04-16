import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Raca } from '../models/dropdowns/types/raca';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class RacaService extends CrudServiceNuvem<number, Raca> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/racas', httpClient);
    }

    getListaDeRaças(): Observable<Raca[]> {
        return this.httpClient.get<Raca[]>('pacientes/api/racas');
    }
}
