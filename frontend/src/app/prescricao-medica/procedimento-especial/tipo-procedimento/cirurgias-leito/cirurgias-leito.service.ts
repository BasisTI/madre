import { CirurgiasLeito } from './../../models/cirurgias-leito';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class CirurgiasLeitoService {

    private baseUrl = 'prescricao/api';
    constructor(private http: HttpClient) { }

    listarCirurgiasLeito(): Observable<Array<CirurgiasLeito>> {
        return this.http.get<Array<CirurgiasLeito>>(`${this.baseUrl}/cirurgias-leitos`);

    }
}
