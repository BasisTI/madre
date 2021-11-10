import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { TransferirPacientes } from '@internacao/models/grade-transferir-paciente';
import { Internacao } from '@internacao/models/internacao';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class FormularioTransferirPacienteService {
  private readonly resource = `${api}/internacoes`;

  uriServico: string = '/api/internacoes';

    constructor(private client: HttpClient) {}

    listarGradeTransferiPaciente(): Observable<Array<Internacao>> {
        return this.client.get<Array<Internacao>>(`${this.resource}`);
    }

    transferirPaciente(transferir: Internacao) {
        return this.client.put(`${this.resource}`, transferir);
    }

    delete(id: number): Observable<Internacao> {
      return this.client.delete<Internacao>(`${this.uriServico}/${id}`);
  }
}
