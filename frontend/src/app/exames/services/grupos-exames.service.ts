import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GrupoModel } from "../models/subjects/grupo-model";

@Injectable({
  providedIn: 'root'
})
export class GruposExamesService {

  private readonly grupoURL = '/madreexames/api'

  constructor(
    private client: HttpClient
  ) { }

  public GetGrupos(): Observable<Array<GrupoModel>> {
    return this.client.get<Array<GrupoModel>>(`${this.grupoURL}/grupo-agendamento-exames`);
  }
}
