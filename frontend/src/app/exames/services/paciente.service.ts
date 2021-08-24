
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paciente } from '../models/subjects/paciente-model';


@Injectable({
  providedIn: 'root'
})
export class PacientesService{
    private readonly apiUrl = 'pacientes/api';


  private readonly resource = `${this.apiUrl}/pre-cadastro-pacientes`;

  constructor(private client: HttpClient) {}

  public getPaciente(): Observable<Array<Paciente>> {
    return this.client.get<Array<Paciente>>(`${this.resource}`);
  }

}