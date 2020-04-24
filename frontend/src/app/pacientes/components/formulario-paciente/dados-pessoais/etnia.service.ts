import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Etnia } from '../../../models/dropdowns/types/etnia';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class EtniaService extends CrudServiceNuvem<number, Etnia> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/etnias?sort=valor', httpClient);
    }

    getListaDeEtnias(): Observable<Etnia[]> {
        return this.httpClient.get<Etnia[]>('pacientes/api/etnias');
    }
}
