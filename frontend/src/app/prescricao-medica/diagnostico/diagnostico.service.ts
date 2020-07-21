import { PrescricaoDiagnostico } from './models/prescricaoDiagnostico';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class DiagnosticoService {

    constructor(private http: HttpClient) { }

    private baseUrl = '/prescricao/api';

    prescreverDiagnostico(prescricao: PrescricaoDiagnostico){
        return this.http.post(`${this.baseUrl}/prescricao-diagnosticos`, prescricao);
    }

}
