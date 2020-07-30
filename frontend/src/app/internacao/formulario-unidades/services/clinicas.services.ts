import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';
import { Clinica } from '../models/dropwdowns/Clinica';

@Injectable({
    providedIn: 'root',
})
export class ClinicaService extends CrudServiceNuvem<number, Clinica> {
    constructor(private httpClient: HttpClient) {
        super('internacao/api/clinicas?sort=descricao', httpClient);
    }

    getListaDeClinicas(): Observable<Clinica[]> {
        let params = new HttpParams();
        params = params.set('size', '30');

        return this.httpClient.get<Clinica[]>('internacao/api/clinicas', { params });
    }
}
