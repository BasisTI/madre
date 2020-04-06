import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class PrescricaoMedicaDietaService {

    private baseUrl = '/prescricao/api';

    constructor(private http: HttpClient) { }

    listar(): Observable<any> {
        return this.http.get<any>(`${this.baseUrl}/prescricao-dieta`);
    }

    adicionar(dieta: any): Observable<any>{
        return this.http.post<any>(`${this.baseUrl}/prescricao-dieta`, dieta);
    }
}
