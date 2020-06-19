import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { TipoUnidade } from './../models/dropwdowns/TipoUnidade';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class TipoUnidadeService extends CrudServiceNuvem<number, TipoUnidade> {
    constructor(private httpClient: HttpClient) {
        super('internacao/api//tipo-unidades?sort=nome', httpClient);
    }

    getListaDeTiposUnidades(): Observable<TipoUnidade[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<TipoUnidade[]>('internacao/api//tipo-unidades', { params });
    }
}
