import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Almoxarifado } from './../models/dropwdowns/almoxarifado';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class AlmoxarifadoService extends CrudServiceNuvem<number, Almoxarifado> {
    constructor(private httpClient: HttpClient) {
        super('internacao/api/almoxarifados?sort=nome', httpClient);
    }

    getListaDeAlmoxarifados(): Observable<Almoxarifado[]> {
        return this.httpClient.get<Almoxarifado[]>('internacao/api/almoxarifados');
    }
}
