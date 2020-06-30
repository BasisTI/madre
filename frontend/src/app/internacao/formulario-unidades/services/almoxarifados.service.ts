import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Almoxarifado } from '../models/dropwdowns/almoxarifado';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class AlmoxarifadosService extends CrudServiceNuvem<number, Almoxarifado> {
    constructor(private httpClient: HttpClient) {
        super('madresuprimentos/api/almoxarifados?sort=descricao', httpClient);
    }

    getListaDeAlmoxarifados(): Observable<Almoxarifado[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<Almoxarifado[]>('madresuprimentos/api/almoxarifados', {
            params,
        });
    }
}
