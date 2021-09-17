import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { LaboratorioExternoModel } from "../models/subjects/laboratorioExterno-model";


@Injectable({
  providedIn: 'root'
})
export class LaboratorioExternoService {

  private readonly grupoURL = '/madreexames/api'

  constructor(
    private client: HttpClient
  ) { }
  public getLaboratorioExterno(): Observable<Array<LaboratorioExternoModel>> {
    return this.client.get<Array<LaboratorioExternoModel>>(`${this.grupoURL}/laboratorio-externos`);
  }
}
