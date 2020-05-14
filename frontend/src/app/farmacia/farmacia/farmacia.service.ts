import { Pageable } from './../../shared/pageable';
import { Observable } from 'rxjs';
import { Unidade } from './dispensacao/unidade';
import { Prescricao } from './dispensacao/prescricao';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class FarmaciaService {
    constructor(private httpServe: HttpClient) {}
    private readonly apiUrl = '/farmacia/api';

    getPrescricao(nome: string, value, local: string): Observable<Pageable<Prescricao>> {
        return this.httpServe.get<Pageable<Prescricao>>(`${this.apiUrl}/prescricao`, {
            params: new HttpParams().set('nome', nome).set('dataInicio', value).set('local', local),
        });
    }

    getResult(nome: string): Observable<Array<Unidade>> {
        return this.httpServe.get<Array<Unidade>>(`${this.apiUrl}/prescricao`, {
            params: new HttpParams().set('unidade', nome),
        });
    }
}
