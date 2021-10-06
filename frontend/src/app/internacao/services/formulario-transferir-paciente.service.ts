import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormularioTransferirPaciente } from './../models/transferir-paciente';

@Injectable({
  providedIn: 'root'
})
export class FormularioTransferirPacienteService {

  private readonly apiUrl = '/madreexames/api/anticoagulantes';

  constructor(private httpService: HttpClient) { }

  cadastrarTransferirPaciente(formularioTransferirPaciente:FormularioTransferirPaciente)
  {
    return this.httpService.post(this.apiUrl, formularioTransferirPaciente);
  }
}
