import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class PrescricaoMedicaDietaService {

    private baseUrl = '/prescricao/api';

    constructor(private http: HttpClient,
        ) { }

    listarDieta(id: number): Observable<any>{
        return this.http.get(`${this.baseUrl}/prescricao-dieta/paciente/${id}`);

    }

    listarTiposItens(): Observable<any> {
        return this.http.get(`${this.baseUrl}/tipo-item`);
    }

    listarTiposAprazamentos(): Observable<any> {
        return this.http.get(`${this.baseUrl}/tipo-aprazamento`);
    }

    adicionar(dieta: any): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/prescricao-dieta`, dieta);
    }

}
