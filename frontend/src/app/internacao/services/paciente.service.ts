import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paciente } from '@internacao/models/paciente';
import { HttpClient } from '@angular/common/http';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class PacienteService {
    private readonly resource = `${api}/pacientes`;

    constructor(private client: HttpClient) {
    }

    public obterPacientePorId(identificador: number): Observable<Paciente> {
        return this.client.get<Paciente>(`${this.resource}/${identificador}`);
    }
}
