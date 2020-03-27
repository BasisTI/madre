import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class PrescricaoMedicaService{
    baseUrl = '/prescricao/api';
    constructor(private http: HttpClient){ }

    listarPacientes() {
       return this.http.get(`${this.baseUrl}/pacientes/listarPacientes`).toPromise()
       .then(response => {
           console.log(response);
       });
    }
}
