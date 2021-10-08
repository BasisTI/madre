import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';

@Injectable({
  providedIn: 'root'
})
export class FormularioDarAltaAoPacienteService {

    private readonly URL = 'internacao/api';

  constructor(private httpClient: HttpClient) { }

  public getDarAltaAoPaciente(
    id: string,
    pacienteId: string,
    leitosId: string,
    unidadeFuncionalId: string,
    especialidadeId: string,
    convenidoDeSaudeId: string
  ): Observable<DarAltaAoPaciente[]> {
      return this.httpClient.get<DarAltaAoPaciente[]> (`${this.URL}/_search/dar-alta-ao-paciente`, {
          params: new HttpParams()
            .set('id', id)
            .set('pacienteId', pacienteId)
            .set('leitosId', leitosId)
            .set('unidadeFuncionalId', unidadeFuncionalId)
            .set('especialidadeId', especialidadeId)
            .set('convenidoSaudeId', convenidoDeSaudeId)
      });
  }
}
