import { Paciente } from './../pacientes/components/formulario-paciente/models/paciente';
import { CRM } from './../internacao/models/crm';

import { Especialidade } from './../internacao/models/especialidade';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConsultaEmergenciaModel } from './consulta-emergencia-model';
import { Observable } from 'rxjs';
import { ConsultaCalendarioModel } from './consulta-calendario-model ';

@Injectable({
    providedIn: 'root',
})
export class ConsultaService {
    private readonly apiUrl = 'madreconsulta/api';
    private readonly intUrl = 'internacao/api';
    private readonly pacUrl = 'pacientes/api';

    constructor(private client: HttpClient) {}

    public cadastrarConsultas(consultas: ConsultaEmergenciaModel) {
        return this.client.post(`${this.apiUrl}/consultas-emergencias`, consultas);
    }

    public buscarEspecialidades(): Observable<Array<Especialidade>> {
        return this.client.get<Array<Especialidade>>(`${this.intUrl}/especialidades`);
    }
    public buscarProfissionais(): Observable<Array<CRM>> {
        return this.client.get<Array<CRM>>(`${this.intUrl}/crms`);
    }
    public buscarPaciente(): Observable<Array<Paciente>> {
        return this.client.get<Array<Paciente>>(`${this.pacUrl}/pacientes`);
    }
    public obterConsultaCalendario(): Observable<Array<ConsultaCalendarioModel>> {
        return this.client.get<Array<ConsultaCalendarioModel>>(
            `${this.apiUrl}/consultas-emergencias/calendario`,
        );
    }
}
