import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { GrupoModel } from "../models/subjects/grupo-model";
import { api } from '@internacao/api';

@Injectable({
  providedIn: 'root'
})
export class UnidadeFuncionalService {

  private readonly unidadesUrl = `${api}/unidades-funcionais`;

  constructor(
    private client: HttpClient
  ) { }

  public GetUnidades(): Observable<Array<GrupoModel>> {
    return this.client.get<Array<GrupoModel>>(this.unidadesUrl);
  }
}
