import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Nacionalidade } from '../models/dropdowns/types/nacionalidade';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class NacionalidadeService extends CrudServiceNuvem<number, Nacionalidade> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/nacionalidades?sort=valor', httpClient);
    }

    getListaDeNacionalidades(): Observable<Nacionalidade[]> {
        return this.httpClient.get<Nacionalidade[]>('pacientes/api/nacionalidades');
    }
}
