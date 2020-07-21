import { Pageable } from './../../../../shared/pageable';
import { TipoProcedimento } from './../../models/tipo-procedimento';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class DiversosService {

    private baseUrl = 'prescricao/api';
    constructor(private http: HttpClient) { }

    listarEspeciaisDiversos(): Observable<any> {
        return this.http.get<any>(`${this.baseUrl}/tipo-procedimento/diversos`);

    }
}
