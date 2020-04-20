import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

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

    buscarId(id: number): Observable<any>{
        return this.http.get(`${this.baseUrl}/pacientes/${id}`);
    }

    adicionar(dieta: any): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/prescricao-dieta`, dieta);
    }

}
