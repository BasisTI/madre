import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Justificativa } from '../models/dropdowns/types/justificativa';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class JustificativaService extends CrudServiceNuvem<number, Justificativa> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/justificativas?sort=valor', httpClient);
    }

    getListaDeJustificativas(): Observable<Justificativa[]> {
        return this.httpClient.get<Justificativa[]>('pacientes/api/justificativas');
    }
}
