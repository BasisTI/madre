import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Paciente} from './paciente';
import { map } from 'micro-dash';

@Injectable()
export class PrescricaoMedicaService{
    baseUrl = '/prescricao/api';
    constructor(private http: HttpClient){}

    listarPacientes(): Observable<Paciente[]> {
       return this.http.get<Paciente[]>(`${this.baseUrl}/pacientes/listarPacientes`);
    }
}
