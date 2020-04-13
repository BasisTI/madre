import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PacienteSummary } from './interfaces/paciente.summary';
import { Pageable } from '../shared/pageable';

@Injectable({
  providedIn: 'root',
})
export class PacientesService {
  private readonly apiUrl = 'pacientes/api/pacientes';

  constructor(private httpService: HttpClient) {}

  getListaDePacientes(): Observable<Pageable<PacienteSummary>> {
    return this.httpService.get<Pageable<PacienteSummary>>(`${this.apiUrl}/lista-de-pacientes`);
  }
}
