import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class PrescricaoMedicaService {
    baseUrl = '/prescricao/api';
    constructor(private http: HttpClient) { }

    buscarIdPaciente(id: number): Observable<any> {
        return this.http.get(`${this.baseUrl}/pacientes/${id}`);
    }

    listarPacientes(): Observable<any> {
        return this.http.get(`${this.baseUrl}/pacientes`);
    }
}
