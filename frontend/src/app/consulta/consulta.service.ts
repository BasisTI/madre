import { Pageable } from './../shared/pageable';
import { Especialidade } from './../internacao/models/especialidade';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConsultaEmergenciaModel } from './consulta-emergencia-model';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ConsultaService {
    private readonly apiUrl = 'madreconsulta/api';
    private readonly internacaoUrl = 'internacao/api';

    constructor(private httpService: HttpClient) {}

    cadastrarConsultas(consultas: ConsultaEmergenciaModel) {
        return this.httpService.post(`${this.apiUrl}/consultas-emergencias`, consultas);
    }
}
