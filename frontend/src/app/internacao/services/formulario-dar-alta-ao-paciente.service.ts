import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';

@Injectable({
  providedIn: 'root'
})
export class FormularioDarAltaAoPacienteService {

    private readonly URL = 'internacao/api';

  constructor(private httpClient: HttpClient) { }

  formularioDarAltaPaciente(darAltaAoPaciente: DarAltaAoPaciente){
      return this.httpClient.post(`${this.URL}/pacientes`, darAltaAoPaciente)
  }
}
