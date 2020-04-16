import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { GrauDeParentesco } from '../models/dropdowns/types/grau-de-parentesco';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class GrauDeParentescoService extends CrudServiceNuvem<number, GrauDeParentesco> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/grau-de-parentescos', httpClient);
    }

    getListaDeGrausDeParentesco(): Observable<GrauDeParentesco[]> {
        return this.httpClient.get<GrauDeParentesco[]>('pacientes/api/grau-de-parentescos');
    }
}
