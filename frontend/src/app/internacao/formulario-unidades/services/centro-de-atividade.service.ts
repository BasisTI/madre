import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CentroDeAtividade } from './../models/dropwdowns/centro-de-atividade';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CentroService extends CrudServiceNuvem<number, CentroDeAtividade> {
    constructor(private httpClient: HttpClient) {
        super('madresuprimentos/api/centros-de-atividade?sort=descricao', httpClient);
    }

    getListaDeCentros(): Observable<CentroDeAtividade[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<CentroDeAtividade[]>(
            'madresuprimentos/api/centros-de-atividade',
            {
                params,
            },
        );
    }
}
