import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ocupacao } from '../models/dropdowns/types/ocupacao';

@Injectable({
    providedIn: 'root',
})
export class OcupacaoService extends CrudServiceNuvem<number, Ocupacao> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/ocupacaos?sort=valor', httpClient);
    }

    getListaDeOcupacoes(): Observable<Ocupacao[]> {
        return this.httpClient.get<Ocupacao[]>('pacientes/api/ocupacaos');
    }
}
