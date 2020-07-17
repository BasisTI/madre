import { ConsultaEmergenciaModel } from './models/consulta-emergencia-model';
import { PacienteModel } from './models/paciente-model';
import { CRM } from './../internacao/models/crm';
import { Especialidade } from './../internacao/models/especialidade';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ConsultaService {
    private readonly apiUrl = 'madreconsulta/api';
    private readonly intUrl = 'internacao/api';
    private readonly pacUrl = 'api';

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
    public buscarPaciente(): Observable<Array<PacienteModel>> {
        return this.client.get<Array<PacienteModel>>(`${this.pacUrl}/pacientes`);
    }
    public buscarPacientesPorId(id: number): Observable<any> {
        return this.client.get(`${this.pacUrl}/pacientes/${id}`);
    }
    public buscarConsultaId(id: number): Observable<any> {
        return this.client.get(`${this.apiUrl}/emergencias/${id}`);
    }
    public obterConsultaCalendario(): Observable<Array<ConsultaEmergenciaModel>> {
        return this.client.get<Array<ConsultaEmergenciaModel>>(
            `${this.apiUrl}/consultas-emergencias/calendario`,
        );
    }
}
