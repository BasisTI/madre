import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UF } from './../../../models/dropdowns/types/uf';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class UFService extends CrudServiceNuvem<number, UF> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/ufs?sort=unidadeFederativa', httpClient);
    }

    getListaDeUF(nome): Observable<UF[]> {
        const params = new HttpParams().set('nome', nome);

        if (nome) {
            params.set('nome', nome ? nome : 'nome');
        }

        return this.httpClient.get<UF[]>('pacientes/api/ufs');
    }
}
