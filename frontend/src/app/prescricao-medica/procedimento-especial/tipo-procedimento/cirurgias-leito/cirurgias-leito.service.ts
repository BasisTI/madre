import { TipoProcedimento } from './../../models/tipo-procedimento';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class CirurgiasLeitoService {

    private baseUrl = 'prescricao/api';
    constructor(private http: HttpClient) { }

    listarCirurgiasLeito(): Observable<Array<TipoProcedimento>> {
        return this.http.get<Array<TipoProcedimento>>(`${this.baseUrl}/tipo-procedimentos/teste/cirurgias-leito`);

    }
}
