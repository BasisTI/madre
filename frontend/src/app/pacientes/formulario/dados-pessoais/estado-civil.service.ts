import { EstadoCivil } from '../../models/dropdowns/types/estado-civil';
import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class EstadoCivilService extends CrudServiceNuvem<number, EstadoCivil> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/estado-civils', httpClient);
    }

    getListaDeEstadoCivis(): Observable<EstadoCivil[]> {
        return this.httpClient.get<EstadoCivil[]>('pacientes/api/estado-civils');
    }
}
