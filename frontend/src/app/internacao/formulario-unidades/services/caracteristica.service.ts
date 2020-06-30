import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Caracteristica } from './../models/dropwdowns/caracteristicas';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';
import { Clinica } from '../models/dropwdowns/Clinica';
@Injectable({
    providedIn: 'root',
})
export class CaracteristicaService extends CrudServiceNuvem<number, Caracteristica> {
    constructor(private httpClient: HttpClient) {
        super('internacao/api/caracteristicas?sort=nome', httpClient);
    }

    getListaDeCaracteristicas(): Observable<Clinica[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<Caracteristica[]>('internacao/api/caracteristicas', { params });
    }
}
