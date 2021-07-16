import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { GrupoModel } from "../models/subjects/grupo-model";
import { api } from '@internacao/api';

@Injectable({
  providedIn: 'root'
})
export class UnidadeFuncionalService {

  private readonly resource = api;

  constructor(
    private client: HttpClient
  ) { }

  public GetGrupos(): Observable<Array<GrupoModel>> {
    return this.client.get<Array<GrupoModel>>(`${this.resource}/unidades-funcionais`);
  }
}
