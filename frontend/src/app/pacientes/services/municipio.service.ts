import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Municipio } from '../models/dropdowns/types/municipio';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class MunicipioService extends CrudServiceNuvem<number, Municipio> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/municipios', httpClient);
    }

    getListaDeMunicipios(): Observable<Municipio[]> {
        return this.httpClient.get<Municipio[]>('pacientes/api/municipios');
    }
}
