import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Naturalidade } from '../models/dropdowns/types/naturalidade';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class NaturalidadeService extends CrudServiceNuvem<number, Naturalidade> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/municipios/_uf?sort=nome', httpClient);
    }

    getListaDeNaturalidades(): Observable<Naturalidade[]> {
        return this.httpClient.get<Naturalidade[]>('pacientes/api/municipios/_uf');
    }
}
