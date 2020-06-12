import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UF } from '../../../models/dropdowns/types/uf';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class UfService extends CrudServiceNuvem<number, UF> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/ufs?sort=unidadeFederativa', httpClient);
    }

    getListaDeUF(): Observable<UF[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<UF[]>('pacientes/api/ufs', { params });
    }
}
