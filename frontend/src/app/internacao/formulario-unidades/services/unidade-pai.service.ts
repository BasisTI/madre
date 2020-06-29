import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { UnidadePai } from '../models/dropwdowns/UnidadePai';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class UnidadePaiService extends CrudServiceNuvem<number, UnidadePai> {
    constructor(private httpClient: HttpClient) {
        super('internacao/api/unidades?sort=descricao', httpClient);
    }

    getListaDeUnidadePais(): Observable<UnidadePai[]> {
        let params = new HttpParams();
        params = params.set('size', '30');
        return this.httpClient.get<UnidadePai[]>('internacao/api/unidades', { params });
    }
}
