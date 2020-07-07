import { CEP } from './../models/cep';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { of, Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CepService extends CrudServiceNuvem<number, CEP> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/cep?sort=cep', httpClient);
    }

    consultaCEP(cep: string): Observable<CEP[]> {
        let params = new HttpParams();
        params = params.append('cep', cep);
        return this.httpClient.get<CEP[]>('pacientes/api/ceps/filtragem', {
            params: params,
        });
    }
}
