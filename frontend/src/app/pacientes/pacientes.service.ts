import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class PacientesService {
  baseUrl = 'pacientes/api';

  constructor(private httpService: HttpClient) {}

  getListaDePacientes(): Promise<any> {
    return this.httpService.get(`${this.baseUrl}/pacientes`).toPromise();
  }
}
