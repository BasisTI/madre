import { api } from '@internacao/api';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Ala } from './../models/Ala';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class AlaService extends CrudServiceNuvem<number, Ala> {
    constructor(private httpClient: HttpClient) {
        super('internacao/api/alas?sort=nome', httpClient);
    }

    getListaDeAlas(): Observable<Ala[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<Ala[]>('internacao/api/alas', { params });
    }
}
