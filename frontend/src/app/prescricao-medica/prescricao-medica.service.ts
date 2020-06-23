import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class PrescricaoMedicaService {
    private readonly baseUrl = '/prescricao/api';
    constructor(private http: HttpClient){}

    buscarIdPaciente(id: number): Observable<any>{
        return this.http.get(`${this.baseUrl}/pacientes/${id}`);
    }

    listarPacientes(): Observable<any> {
       return this.http.get(`${this.baseUrl}/pacientes`);
    }

    pesquisaPaciente(query: string): Observable<any> {
        const params = new HttpParams().set('nome', query);

        return this.http.get(
            `${this.baseUrl}/pacientes`,
            {
                params,
            },
        );
    }
}
