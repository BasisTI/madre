import { EspeciaisDiversos } from './../../models/especiais-diversos';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})

export class DiversosService {

    private baseUrl = 'prescricao/api';
    constructor(private http: HttpClient) { }

    listarEspeciaisDiversos(): Observable<Array<EspeciaisDiversos>> {
        return this.http.get<Array<EspeciaisDiversos>>(`${this.baseUrl}/especiais-diversos`);

    }
}
