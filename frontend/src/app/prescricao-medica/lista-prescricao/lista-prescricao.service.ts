import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class ListaPrescricaoService {
    constructor(private http: HttpClient) {}

    private readonly baseUrl = '/prescricao/api';

    listarPrescricoes(id: number): Observable<any> {
        return this.http.get(`${this.baseUrl}/_search/prescricao-medicas/${id}`);
    }
}
