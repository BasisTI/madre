import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Almoxarifado } from '../models/dropwdowns/almoxarifado';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class SuprimentosService {
    urlSuprimentos = '/suprimentos/api';

    constructor(private http: HttpClient) {}

    listaDeAlmoxarifados(): Observable<any> {
        return this.http.get(`${this.urlSuprimentos}/almoxarifados`);
    }

    listaDeCentroDeAtividades(): Observable<any> {
        return this.http.get(`${this.urlSuprimentos}/centro-de-atividades`);
    }
}
