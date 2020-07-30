import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { Paciente } from '@internacao/models/paciente';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class PacienteService {
    private readonly resource = `${api}/pacientes`;

    constructor(private client: HttpClient) {}

    public obterPacientePorId(identificador: number): Observable<Paciente> {
        return this.client.get<Paciente>(`${this.resource}/${identificador}`);
    }

    public obterPacientePorProntuario(prontuario: number): Observable<Paciente> {
        return this.client.get<Paciente>(`${this.resource}/prontuario/${prontuario}`);
    }
}
