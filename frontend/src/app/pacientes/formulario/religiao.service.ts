import { Religiao } from '../models/dropdowns/types/religiao';
import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ReligiaoService extends CrudServiceNuvem<number, Religiao> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/religiaos?sort=valor', httpClient);
    }

    getListaDeReligioes(): Observable<Religiao[]> {
        return this.httpClient.get<Religiao[]>('pacientes/api/religiaos');
    }
}
